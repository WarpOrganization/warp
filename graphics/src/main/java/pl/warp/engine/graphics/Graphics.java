package pl.warp.engine.graphics;

import pl.warp.engine.core.*;
import pl.warp.engine.core.updater.UpdaterTask;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.texture.Texture2D;
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
    private Sink<Texture2D> output;

    private SyncEngineThread thread;
    private Display display;
    private GLFWWindowManager windowManager;
    private Environment environment;

    private EnginePipeline enginePipeline;

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
        this.thread.start();
    }

    private void createCameraTask() {
        thread.scheduleTask(new UpdaterTask(mainViewCamera));
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
        this.enginePipeline = new EnginePipeline(config, context, thread, this);
        enginePipeline.createPipeline(output, mainViewCamera, environment);
        RenderingTask task = new RenderingTask(display, windowManager, enginePipeline.getPipeline());
        thread.scheduleTask(task);
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
        return enginePipeline.getCustomRenderersManager();
    }

    public EnginePipeline getEnginePipeline() {
        return enginePipeline;
    }


    public Camera getMainViewCamera() {
        return mainViewCamera;
    }

    public void setMainViewCamera(Camera mainViewCamera) {
        this.mainViewCamera = mainViewCamera;
        this.enginePipeline.setMainViewCamera(mainViewCamera);
    }
}
