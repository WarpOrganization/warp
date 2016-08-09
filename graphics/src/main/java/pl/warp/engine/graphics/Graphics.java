package pl.warp.engine.graphics;

import pl.warp.engine.core.*;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.particles.ParticleEmitterRenderer;
import pl.warp.engine.graphics.particles.ParticleSystemRenderer;
import pl.warp.engine.graphics.pipeline.MultisampleTextureRenderer;
import pl.warp.engine.graphics.pipeline.OnScreenRenderer;
import pl.warp.engine.graphics.pipeline.Pipeline;
import pl.warp.engine.graphics.pipeline.builder.PipelineBuilder;
import pl.warp.engine.graphics.mesh.MeshRenderer;
import pl.warp.engine.graphics.postprocessing.BloomRenderer;
import pl.warp.engine.graphics.postprocessing.HDRRenderer;
import pl.warp.engine.graphics.postprocessing.lens.LensEnviromentFlareRenderer;
import pl.warp.engine.graphics.postprocessing.lens.LensFlareRenderer;
import pl.warp.engine.graphics.skybox.SkyboxRenderer;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 21
 */
public class Graphics {

    private static final int UPS_SAMPLE_SIZE = 50;

    private EngineContext context;
    private Camera mainViewCamera;
    private RenderingConfig config;

    private SyncEngineThread thread;
    private Display display;
    private GLFWWindowManager windowManager;
    private Environment environment;

    public Graphics(EngineContext context, Camera mainViewCamera, RenderingConfig config) {
        this.context = context;
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
        createRenderingTask();
        thread.start();
    }

    private void createWindow() {
        this.display = new Display(config.getDisplay().isFullscreen(), config.getDisplay().getWidth(), config.getDisplay().getHeight());
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
        Pipeline pipeline = createPipeline();
        RenderingTask task = new RenderingTask(context, display, windowManager, pipeline);
        thread.scheduleTask(task);
    }

    private Pipeline createPipeline() {
        MeshRenderer meshRenderer = new MeshRenderer(mainViewCamera, environment);
        SkyboxRenderer skyboxRenderer = new SkyboxRenderer(mainViewCamera);
        ParticleSystemRenderer particleSystemRenderer = new ParticleSystemRenderer(mainViewCamera);
        ParticleEmitterRenderer emitterRenderer = new ParticleEmitterRenderer();
        LensEnviromentFlareRenderer enviromentFlareRenderer = new LensEnviromentFlareRenderer(environment);
        Renderer[] renderers = {skyboxRenderer, meshRenderer, particleSystemRenderer, emitterRenderer, enviromentFlareRenderer};
        SceneRenderer sceneRenderer = new SceneRenderer(context.getScene(), config, renderers);
        MultisampleTextureRenderer textureRenderer = new MultisampleTextureRenderer(config);
        BloomRenderer bloomRenderer = new BloomRenderer(config);
        LensFlareRenderer flareRenderer = new LensFlareRenderer(mainViewCamera, environment, config);
        HDRRenderer hdrRenderer = new HDRRenderer(config);
        OnScreenRenderer onScreenRenderer = new OnScreenRenderer();
        return PipelineBuilder.from(sceneRenderer).via(textureRenderer).via(bloomRenderer).via(hdrRenderer).via(flareRenderer).to(onScreenRenderer);
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
}
