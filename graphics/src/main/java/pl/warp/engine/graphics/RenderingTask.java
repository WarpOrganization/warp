package pl.warp.engine.graphics;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.*;
import pl.warp.engine.graphics.pipeline.Pipeline;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.WindowManager;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */
public class RenderingTask extends EngineTask {

    private static Logger logger = Logger.getLogger(RenderingTask.class);

    private EngineContext context;
    private Display display;
    private WindowManager windowManager;
    private Pipeline pipeline;

    public RenderingTask(EngineContext context, Display display, WindowManager windowManager, Pipeline pipeline) {
        this.context = context;
        this.display = display;
        this.windowManager = windowManager;
        this.pipeline = pipeline;
    }

    @Override
    protected void onInit() {
        logger.info("Initializing rendering task.");
        windowManager.makeWindow(display);
        logger.info("Window created.");
        createOpenGL();
        logger.info("OpenGL capabilities created.");
        pipeline.init();
        logger.info("Initialized pipeline.");
    }

    private void createOpenGL() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    protected void onClose() {
        windowManager.closeWindow();
    }

    @Override
    public void update(long delta) {
        pipeline.update(delta);
        windowManager.updateWindow();
    }

}
