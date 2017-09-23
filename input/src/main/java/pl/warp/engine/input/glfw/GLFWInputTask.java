package pl.warp.engine.input.glfw;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.engine.graphics.window.WindowManager;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 23
 */
@Service
@RegisterTask(thread = "graphics")
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
