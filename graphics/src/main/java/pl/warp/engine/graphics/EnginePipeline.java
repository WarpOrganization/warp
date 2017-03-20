package pl.warp.engine.graphics;

import org.joml.Vector3f;
import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.EngineThread;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.mesh.MeshRenderer;
import pl.warp.engine.graphics.particles.ParticleAnimatorTask;
import pl.warp.engine.graphics.particles.ParticleSystemRenderer;
import pl.warp.engine.graphics.particles.ParticleSystemStorage;
import pl.warp.engine.graphics.particles.ParticleSystemsStorageUpdater;
import pl.warp.engine.graphics.pipeline.*;
import pl.warp.engine.graphics.pipeline.builder.PipelineBuilder;
import pl.warp.engine.graphics.pipeline.rendering.MultisampleTextureRenderer;
import pl.warp.engine.graphics.pipeline.rendering.effects.barrelchroma.BarrelChromaEffect;
import pl.warp.engine.graphics.pipeline.rendering.effects.distortedscreen.DistortedScreenEffect;
import pl.warp.engine.graphics.pipeline.rendering.effects.monochromatic.MonochromaticEffect;
import pl.warp.engine.graphics.pipeline.rendering.effects.mosaic.MosaicEffect;
import pl.warp.engine.graphics.pipeline.rendering.effects.screen.ScreenEffect;
import pl.warp.engine.graphics.postprocessing.BloomRenderer;
import pl.warp.engine.graphics.postprocessing.HDRRenderer;
import pl.warp.engine.graphics.postprocessing.WeightedTexture2D;
import pl.warp.engine.graphics.postprocessing.lens.LensEnvironmentFlareRenderer;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftProperty;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftRenderer;
import pl.warp.engine.graphics.skybox.SkyboxRenderer;
import pl.warp.engine.graphics.texture.Texture2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2017-03-19 at 23
 */
public class EnginePipeline {

    private RenderingConfig config;
    private EngineContext context;
    private EngineThread graphicsThread;
    private Graphics graphics;

    private Pipeline pipeline;

    private SceneRenderer sceneRenderer;
    private ComponentRenderer componentRenderer;

    private MeshRenderer meshRenderer;
    private SkyboxRenderer skyboxRenderer;
    private ParticleSystemStorage particleSystemStorage;
    private ParticleSystemsStorageUpdater particleSystemRecorder;
    private ParticleSystemRenderer particleSystemRenderer;
    private CustomRenderersManager customRenderersManager;

    public EnginePipeline(RenderingConfig config, EngineContext context, EngineThread graphicsThread, Graphics graphics) {
        this.config = config;
        this.context = context;
        this.graphicsThread = graphicsThread;
        this.graphics = graphics;
    }

    public void createPipeline(Sink<Texture2D> output, Camera mainViewCamera, Environment environment) {
        sceneRenderer = getSceneRenderer(mainViewCamera, environment);
        particleSystemRenderer = new ParticleSystemRenderer(mainViewCamera, particleSystemStorage);
        MultisampleTextureRenderer textureRenderer = new MultisampleTextureRenderer(config);
        PipelineBuilder<Texture2D> pipeline = PipelineBuilder
                .from(sceneRenderer)
                .via(particleSystemRenderer)
                .via(textureRenderer);
        pipeline = createPostprocessing(pipeline, environment);
        pipeline = createEffects(pipeline);
        this.pipeline = pipeline.to(output, graphics);
    }

    private PipelineBuilder<Texture2D> createEffects(PipelineBuilder<Texture2D> pipeline) {
        PipelineBuilder p = pipeline;
        if (config.getEffects().isBarrelchroma()) p = p.via(new BarrelChromaEffect(30));
        if (config.getEffects().isMosaic()) p = p.via(new MosaicEffect(0.003f, 0.0024f));
        if (config.getEffects().isDistorted()) p = p.via(new DistortedScreenEffect());
        if (config.getEffects().isMonochromatic()) p = p.via(new MonochromaticEffect(new Vector3f(1)));
        if (config.getEffects().isScreen()) p = p.via(new ScreenEffect());
        return p;
    }

    private PipelineBuilder<Texture2D> createPostprocessing(PipelineBuilder<Texture2D> pipeline, Environment environment) {
        if (config.areLensEnabled()) pipeline = createFlares(pipeline, environment);
        List<Flow<Texture2D, WeightedTexture2D>> postprocesses = new ArrayList<>();
        LazyFlow<Texture2D, WeightedTexture2D> sceneFlow = new LazyFlow<>(
                new WeightedTexture2D(null, 1.0f, 1.0f),
                (i, o) -> o.setTexture(i));
        postprocesses.add(sceneFlow);
        if (config.isBloomEnabled()) postprocesses.add(createBloom());
        if (config.isSunshaftEnabled()) postprocesses.add(createSunshaft());
        MultiFlow<Texture2D, WeightedTexture2D> postprocessing = new MultiFlow<>(postprocesses.stream().toArray(Flow[]::new), WeightedTexture2D[]::new);
        HDRRenderer hdrRenderer = new HDRRenderer(config);
        return pipeline.via(postprocessing).via(hdrRenderer);
    }

    private SceneRenderer getSceneRenderer(Camera mainViewCamera, Environment environment) {
        this.customRenderersManager = new CustomRenderersManager(graphics);
        meshRenderer = new MeshRenderer(mainViewCamera, environment);
        skyboxRenderer = new SkyboxRenderer(mainViewCamera);
        particleSystemStorage = new ParticleSystemStorage();
        particleSystemRecorder = new ParticleSystemsStorageUpdater(particleSystemStorage);
        createParticleAnimator();
        LensEnvironmentFlareRenderer environmentFlareRenderer = new LensEnvironmentFlareRenderer(environment);
        Renderer[] renderers = {skyboxRenderer, meshRenderer, particleSystemRecorder, environmentFlareRenderer};
        componentRenderer = new ComponentRenderer(renderers);
        return new SceneRenderer(context.getScene(), config, componentRenderer);
    }

    private void createParticleAnimator() {
        EngineTask particleAnimatorTask = new ParticleAnimatorTask(particleSystemStorage);
        graphicsThread.scheduleTask(particleAnimatorTask);
    }

    private Flow<Texture2D, WeightedTexture2D> createBloom() {
        return new BloomRenderer(config);
    }

    private Flow<Texture2D, WeightedTexture2D> createSunshaft() {
        SunshaftProperty property = new SunshaftProperty();
        context.getScene().addProperty(property);
        return new SunshaftRenderer(sceneRenderer, property.getSource(), config, componentRenderer);
    }

    private PipelineBuilder<Texture2D> createFlares(PipelineBuilder<Texture2D> builder, Environment environment) {
        LensFlareRenderer flareRenderer = new LensFlareRenderer(environment, config);
        return builder.via(flareRenderer);
    }

    public void setMainViewCamera(Camera mainViewCamera) {
        meshRenderer.setCamera(mainViewCamera);
        skyboxRenderer.setCamera(mainViewCamera);
        particleSystemRenderer.setCamera(mainViewCamera);
        customRenderersManager.setMainViewCamera(mainViewCamera);
    }

    public void resize(int newWidth, int newHeight) {
        pipeline.resize(newWidth, newHeight);
        customRenderersManager.resize(newWidth, newHeight);
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public SceneRenderer getSceneRenderer() {
        return sceneRenderer;
    }

    public ComponentRenderer getComponentRenderer() {
        return componentRenderer;
    }

    public MeshRenderer getMeshRenderer() {
        return meshRenderer;
    }

    public SkyboxRenderer getSkyboxRenderer() {
        return skyboxRenderer;
    }

    public ParticleSystemStorage getParticleSystemStorage() {
        return particleSystemStorage;
    }

    public ParticleSystemsStorageUpdater getParticleSystemRecorder() {
        return particleSystemRecorder;
    }

    public ParticleSystemRenderer getParticleSystemRenderer() {
        return particleSystemRenderer;
    }

    public CustomRenderersManager getCustomRenderersManager() {
        return customRenderersManager;
    }
}
