package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.SMAARenderer;
import net.warpgame.engine.graphics.window.WindowTask;
import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.GLErrors;
import net.warpgame.engine.graphics.rendering.scene.SceneRenderer;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceRenderer;

/**
 * @author Jaca777
 * Created 2016-06-25 at 21
 */

@Service
@RegisterTask(thread = "graphics")
@ExecuteAfterTask(WindowTask.class)
public class RenderingTask extends EngineTask {

    private SceneRenderer sceneRenderer;
    private ScreenspaceRenderer screenspaceRenderer;
    private SMAARenderer smaaRenderer;

    public RenderingTask(
            SceneRenderer sceneRenderer,
            ScreenspaceRenderer screenspaceRenderer,
            SMAARenderer smaaRenderer
    ) {
        this.sceneRenderer = sceneRenderer;
        this.screenspaceRenderer = screenspaceRenderer;
        this.smaaRenderer = smaaRenderer;
    }

    private static Logger logger = Logger.getLogger(RenderingTask.class);

    @Override
    protected void onInit() {
        logger.info("Initializing rendering task.");
        createOpenGL();
        logger.info("OpenGL capabilities created.");
        sceneRenderer.init();
        screenspaceRenderer.init();
        smaaRenderer.initialize();
        //pipeline initialization...
        logger.info("Initialized pipeline.");
    }


    private void createOpenGL() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    protected void onClose() {
        sceneRenderer.destroy();
        screenspaceRenderer.destroy();
        smaaRenderer.destroy();
        //destroy pipeline
    }

    @Override
    public void update(int delta) {
        sceneRenderer.update();
        screenspaceRenderer.update();
        smaaRenderer.update();
        GLErrors.checkOGLErrors();
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
