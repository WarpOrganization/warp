package pl.warp.engine.graphics;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import pl.warp.engine.core.*;
import pl.warp.engine.graphics.pipeline.Pipeline;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.graphics.window.WindowManager;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 21
 */
public class RenderingTask extends EngineTask {

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
        windowManager.makeWindow(display);
    }

    @Override
    protected void onClose() {
        windowManager.closeWindow();
    }

    @Override
    public void update(long delta) {
        pipeline.render();
    }
}
