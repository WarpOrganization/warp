package pl.warp.engine.graphics;

import pl.warp.engine.core.*;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.mesh.MeshRenderer;
import pl.warp.engine.graphics.particles.ParticleSystemRenderer;
import pl.warp.engine.graphics.particles.ParticleSystemsStorageRenderer;
import pl.warp.engine.graphics.particles.ParticleSystemStorage;
import pl.warp.engine.graphics.pipeline.*;
import pl.warp.engine.graphics.pipeline.builder.PipelineBuilder;
import pl.warp.engine.graphics.postprocessing.BloomRenderer;
import pl.warp.engine.graphics.postprocessing.HDRRenderer;
import pl.warp.engine.graphics.postprocessing.WeightedTexture2D;
import pl.warp.engine.graphics.postprocessing.lens.LensEnvironmentFlareRenderer;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftProperty;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftRenderer;
import pl.warp.engine.graphics.skybox.SkyboxRenderer;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 21
 */
public class Graphics {

    private static final int UPS_SAMPLE_SIZE = 50;

    private EngineContext context;
    private Camera mainViewCamera;
    private RenderingConfig config;
    private Sink<Texture2D> output;

    private SyncEngineThread thread;
    private Display display;
    private GLFWWindowManager windowManager;
    private Environment environment;
    private MeshRenderer meshRenderer;
    private SkyboxRenderer skyboxRenderer;
    private ParticleSystemStorage particleSystemStorage;
    private ParticleSystemsStorageRenderer particleSystemRecorder;
    private ParticleSystemRenderer particleSystemRenderer;
    private CustomRenderersManager customRenderersManager;

    private Pipeline pipeline;
    private SceneRenderer sceneRenderer;
    private ComponentRenderer componentRenderer;


    public Graphics(EngineContext context, Sink<Texture2D> output, Camera mainViewCamera, RenderingConfig config) {
        this.context = context;
        this.output = output;
        this.mainViewCamera = mainViewCamera;
        this.config = config;
        createThread();
    }

    private void createThread() {
        this.thread = new SyncEngineThread(new SyncTimer(config.getFps()), new OnePerUpdateExecutionStrategy());
    }

    public void create() {
        createWindow();
        createEnvironment();
        createCameraTask();
        createRenderingTask();
        thread.start();
    }

    private void createCameraTask() {
        thread.scheduleTask(new UpdaterTask(d -> mainViewCamera.update()));
    }

    private void createWindow() {
        this.display = config.getDisplay();
        this.windowManager = new GLFWWindowManager(this.thread::interrupt);
        WindowTask windowTask = new WindowTask(windowManager, display);
        thread.scheduleTask(windowTask);
    }

    private void createEnvironment() {
        this.environment = new Environment();
        EnvironmentTask environmentTask = new EnvironmentTask(context.getScene(), environment);
        thread.scheduleTask(environmentTask);
    }

    private void createRenderingTask() {
        pipeline = createPipeline();
        RenderingTask task = new RenderingTask(display, windowManager, pipeline);
        thread.scheduleTask(task);
    }

    private Pipeline createPipeline() {
        sceneRenderer = getSceneRenderer();
        particleSystemRenderer = new ParticleSystemRenderer(mainViewCamera, particleSystemStorage);
        MultisampleTextureRenderer textureRenderer = new MultisampleTextureRenderer(config);
        PipelineBuilder<Texture2D> pipeline = PipelineBuilder
                .from(sceneRenderer)
                .via(particleSystemRenderer)
                .via(textureRenderer);
        pipeline = createPostprocessing(pipeline);
        return pipeline.to(output);
    }

    private PipelineBuilder<Texture2D> createPostprocessing(PipelineBuilder<Texture2D> pipeline) {
        if (config.areLensEnabled()) pipeline = createFlares(pipeline);
        List<Flow<Texture2D, WeightedTexture2D>> postprocesses = new ArrayList<>();
        SimpleFlow<Texture2D, WeightedTexture2D> sceneFlow = new SimpleFlow<>(
                new WeightedTexture2D(null, 1.0f, 1.0f),
                (i, o) -> o.setTexture(i));
        postprocesses.add(sceneFlow);
        if (config.isBloomEnabled()) postprocesses.add(createBloom());
        if(config.isSunshaftEnabled()) postprocesses.add(createSunshaft());
        MultiFlow<Texture2D, WeightedTexture2D> postprocessing = new MultiFlow<>(postprocesses.stream().toArray(Flow[]::new), WeightedTexture2D[]::new);
        HDRRenderer hdrRenderer = new HDRRenderer(config);
        return pipeline.via(postprocessing).via(hdrRenderer);
    }

    private SceneRenderer getSceneRenderer() { this.customRenderersManager = new CustomRenderersManager(this);
        meshRenderer = new MeshRenderer(mainViewCamera, environment);
        skyboxRenderer = new SkyboxRenderer(mainViewCamera);
        particleSystemStorage = new ParticleSystemStorage();
        particleSystemRecorder = new ParticleSystemsStorageRenderer(particleSystemStorage);
        LensEnvironmentFlareRenderer environmentFlareRenderer = new LensEnvironmentFlareRenderer(environment);
        Renderer[] renderers = {skyboxRenderer, meshRenderer, particleSystemRecorder, environmentFlareRenderer};
        componentRenderer = new ComponentRenderer(renderers);
        return new SceneRenderer(context.getScene(), config, componentRenderer);
    }

    private Flow<Texture2D, WeightedTexture2D> createBloom() {
        return new BloomRenderer(config);
    }

    private Flow<Texture2D, WeightedTexture2D> createSunshaft() {
        SunshaftProperty property = new SunshaftProperty();
        context.getScene().addProperty(property);
        return new SunshaftRenderer(sceneRenderer, property.getSource(), config, componentRenderer, this);
    }

    private PipelineBuilder<Texture2D> createFlares(PipelineBuilder<Texture2D> builder) {
        LensFlareRenderer flareRenderer = new LensFlareRenderer(this, environment, config);
        return builder.via(flareRenderer);
    }

    public Graphics setMainViewCamera(Camera mainViewCamera) {
        this.mainViewCamera = mainViewCamera;
        meshRenderer.setCamera(mainViewCamera);
        skyboxRenderer.setCamera(mainViewCamera);
        particleSystemRenderer.setCamera(mainViewCamera);
        customRenderersManager.setMainViewCamera(mainViewCamera);
        return this;
    }

    public void resize(int newWidth, int newHeight) {
        pipeline.resize(newWidth, newHeight);
        customRenderersManager.resize(newWidth, newHeight);
    }

    public EngineThread getThread() {
        return thread;
    }

    public EngineContext getContext() {
        return context;
    }

    public RenderingConfig getConfig() {
        return config;
    }

    public GLFWWindowManager getWindowManager() {
        return windowManager;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void enableUpsLogging() {
        thread.scheduleTask(new UPSCounterTask(UPS_SAMPLE_SIZE, true));
    }

    public void closeWindow() {
        windowManager.closeWindow();
    }

    public Sink<Texture2D> getOutput() {
        return output;
    }

    public CustomRenderersManager getCustomRenderersManager() {
        return customRenderersManager;
    }

    public Camera getMainViewCamera() {
        return mainViewCamera;
    }
}
