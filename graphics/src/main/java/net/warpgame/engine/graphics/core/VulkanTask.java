package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.ZerviceBypass;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.PresentationQueue;
import net.warpgame.engine.graphics.command.QueueFamilyIndices;
import net.warpgame.engine.graphics.command.StandardCommandPool;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.window.SwapChain;
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
    private Device device;
    private Allocator allocator;
    private GraphicsQueue graphicsQueue;
    private PresentationQueue presentationQueue;
    private StandardCommandPool commandPool;
    private SwapChain swapChain;

    public VulkanTask(Instance instance, DebugCallback debugCallback, Window window, PhysicalDevice physicalDevice, Device device, Allocator allocator, GraphicsQueue graphicsQueue, PresentationQueue presentationQueue, QueueFamilyIndices queueFamilyIndices, SwapChain swapChain) {
        this.instance = instance;
        this.debugCallback = debugCallback;
        this.physicalDevice = physicalDevice;
        this.window = window;
        this.device = device;
        this.allocator = allocator;
        this.graphicsQueue = graphicsQueue;
        this.presentationQueue = presentationQueue;
        this.swapChain = swapChain;
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
        device.create();
        allocator.create();
        presentationQueue.create();
        graphicsQueue.create();
        commandPool = new StandardCommandPool(device, graphicsQueue);
        swapChain.create();
    }

    @Override
    public void update(int delta) {
        ZerviceBypass.run = !glfwWindowShouldClose(window.get())  ;
        glfwPollEvents();
    }

    @Override
    protected void onClose() {
        swapChain.destroy();
        commandPool.destroy();
        allocator.destroy();
        device.destroy();
        window.destroy();
        if(ENABLE_VALIDATION_LAYERS) {
            debugCallback.destroy();
        }
        instance.destroy();
    }
}
