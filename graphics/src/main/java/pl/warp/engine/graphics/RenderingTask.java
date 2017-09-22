package pl.warp.engine.graphics;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.WindowManager;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */
public class RenderingTask extends EngineTask {

    private static Logger logger = Logger.getLogger(RenderingTask.class);

    private Display display;
    private WindowManager windowManager;

    public RenderingTask(Display display, WindowManager windowManager) {
        this.display = display;
        this.windowManager = windowManager;
    }

    @Override
    protected void onInit() {
        logger.info("Initializing rendering task.");
        createOpenGL();
        logger.info("OpenGL capabilities created.");
        //initialize pipeline
        logger.info("Initialized pipeline.");
    }


    private void createOpenGL() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    protected void onClose() {
        //destroy pipeline
        windowManager.closeWindow();
    }

    @Override
    public void update(int delta) {
        //update pipeline
        GLErrors.checkOGLErrors();
    }



}
