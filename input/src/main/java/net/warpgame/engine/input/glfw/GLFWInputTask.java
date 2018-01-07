package net.warpgame.engine.input.glfw;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.InsertAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.window.WindowManager;
import net.warpgame.engine.graphics.window.WindowTask;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 23
 */
@Service
@RegisterTask(thread = "graphics")
@InsertAfterTask(WindowTask.class)
public class GLFWInputTask extends EngineTask {

    private GLFWInput input;
    private WindowManager windowManager;

    public GLFWInputTask(GLFWInput input, WindowManager windowManager) {
        this.input = input;
        this.windowManager = windowManager;
    }

    @Override
    protected void onInit() {
        input.init(windowManager.getWindowHandle());
    }

    @Override
    protected void onClose() {
        input.destroy();
    }

    @Override
    public void update(int delta) {
        input.update();
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
