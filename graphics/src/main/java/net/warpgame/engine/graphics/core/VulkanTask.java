package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.window.Window;

import static net.warpgame.engine.graphics.ZerviceBypass.ENABLE_VALIDATION_LAYERS;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;

/**
 * @author MarconZet
 * Created 05.04.2019
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class VulkanTask extends EngineTask {
    private Instance instance;
    private DebugCallback debugCallback;
    private Window window;
    private PhysicalDevice physicalDevice;

    public VulkanTask(Instance instance, DebugCallback debugCallback, Window window, PhysicalDevice physicalDevice) {
        this.instance = instance;
        this.debugCallback = debugCallback;
        this.physicalDevice = physicalDevice;
        this.window = window;
    }

    @Override
    protected void onInit() {
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        if (!glfwVulkanSupported()) {
            throw new AssertionError("GLFW failed to find the Vulkan loader");
        }

        instance.create();
        if(ENABLE_VALIDATION_LAYERS)
            debugCallback.create();
        window.create();
        physicalDevice.create();
    }

    @Override
    public void update(int delta) {
            glfwPollEvents();
    }

    @Override
    protected void onClose() {
        if(ENABLE_VALIDATION_LAYERS)
            debugCallback.destroy();
        window.destroy();
        instance.destroy();
    }
}
