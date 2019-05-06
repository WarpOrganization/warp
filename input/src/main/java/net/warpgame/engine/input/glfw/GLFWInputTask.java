package net.warpgame.engine.input.glfw;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.VulkanTask;
import net.warpgame.engine.graphics.window.Window;

/**
 * @author Jaca777
 *         Created 2016-07-02 at 23
 */
@Service
@Profile("input")
@RegisterTask(thread = "graphics")
@ExecuteAfterTask(VulkanTask.class)
public class GLFWInputTask extends EngineTask {

    private GLFWInput input;
    private Window window;

    public GLFWInputTask(GLFWInput input, Window window) {
        this.input = input;
        this.window = window;
    }

    @Override
    protected void onInit() {
        input.init(window.get());
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
