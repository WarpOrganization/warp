package pl.warp.engine.graphics.input.glfw;

import pl.warp.engine.core.EngineTask;
import pl.warp.engine.graphics.window.GLFWWindowManager;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 23
 */
public class GLFWInputTask extends EngineTask {

    private GLFWInput input;
    private GLFWWindowManager windowManager;

    public GLFWInputTask(GLFWInput input, GLFWWindowManager windowManager) {
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
}
