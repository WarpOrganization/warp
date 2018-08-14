package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.GLErrors;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.SMAARenderer;
import net.warpgame.engine.graphics.rendering.gui.GuiRenderer;
import net.warpgame.engine.graphics.rendering.scene.SceneRenderer;
import net.warpgame.engine.graphics.rendering.screenspace.ScreenspaceRenderer;
import net.warpgame.engine.graphics.window.WindowTask;
import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2016-06-25 at 21
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
@ExecuteAfterTask(WindowTask.class)
public class RenderingTask extends EngineTask {
    private static Logger logger = Logger.getLogger(RenderingTask.class);

    private SceneRenderer sceneRenderer;
    private ScreenspaceRenderer screenspaceRenderer;
    private GuiRenderer guiRenderer;
    private SMAARenderer smaaRenderer;


    public RenderingTask(
            SceneRenderer sceneRenderer,
            ScreenspaceRenderer screenspaceRenderer,
            GuiRenderer guiRenderer,
            SMAARenderer smaaRenderer
    ) {
        this.sceneRenderer = sceneRenderer;
        this.screenspaceRenderer = screenspaceRenderer;
        this.guiRenderer = guiRenderer;
        this.smaaRenderer = smaaRenderer;
    }

    @Override
    protected void onInit() {
        logger.info("Initializing rendering task.");
        createOpenGL();
        logger.info("OpenGL capabilities created.");
        sceneRenderer.init();
        screenspaceRenderer.init();
//        guiRenderer.init();
        smaaRenderer.initialize();
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
        screenspaceRenderer.destroy();
//        guiRenderer.destroy();
        smaaRenderer.destroy();
        //destroy pipeline
    }

    @Override
    public void update(int delta) {
        sceneRenderer.update();
        screenspaceRenderer.update();
//        guiRenderer.update();
        smaaRenderer.update();
        GLErrors.checkOGLErrors();
    }
    @Override
    public int getPriority() {
        return 1;
    }
}
