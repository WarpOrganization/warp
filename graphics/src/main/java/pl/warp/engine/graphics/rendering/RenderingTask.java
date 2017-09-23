package pl.warp.engine.graphics.rendering;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.engine.graphics.GLErrors;
import pl.warp.engine.graphics.rendering.scene.SceneRenderer;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */

@Service
@RegisterTask(thread = "graphics")
public class RenderingTask extends EngineTask {

    private SceneRenderer sceneRenderer;

    public RenderingTask(SceneRenderer sceneRenderer) {
        this.sceneRenderer = sceneRenderer;
    }

    private static Logger logger = Logger.getLogger(RenderingTask.class);

    @Override
    protected void onInit() {
        logger.info("Initializing rendering task.");
        createOpenGL();
        logger.info("OpenGL capabilities created.");
        sceneRenderer.init();
        //pipeline initialization...
        logger.info("Initialized pipeline.");
    }


    private void createOpenGL() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    protected void onClose() {
        sceneRenderer.destroy();
        //destroy pipeline
    }

    @Override
    public void update(int delta) {
        sceneRenderer.update();
        GLErrors.checkOGLErrors();
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
