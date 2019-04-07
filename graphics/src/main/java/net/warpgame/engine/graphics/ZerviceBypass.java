package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.core.*;
import net.warpgame.engine.graphics.queue.QueueFamilyIndices;
import net.warpgame.engine.graphics.window.SwapChainSupportDetails;
import net.warpgame.engine.graphics.window.Window;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME;
import static org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME;

/**
 * @author MarconZet
 * Created 05.04.2019
 */
public class ZerviceBypass {
    //TODO this staff should come form config, and not be hardcoded
    public static final boolean ENABLE_VALIDATION_LAYERS = true;
    public static final String[] VALIDATION_LAYERS = {"VK_LAYER_LUNARG_standard_validation", "VK_LAYER_RENDERDOC_Capture"};
    public static final String[] VALIDATION_LAYERS_INSTANCE_EXTENSIONS = {VK_EXT_DEBUG_REPORT_EXTENSION_NAME};
    public static final String[] INSTANCE_EXTENSIONS = {VK_KHR_SURFACE_EXTENSION_NAME};
    public static final String[] DEVICE_EXTENSIONS = {VK_KHR_SWAPCHAIN_EXTENSION_NAME};
    public static int DEBUG_REPORT =
            VK_DEBUG_REPORT_ERROR_BIT_EXT |
            VK_DEBUG_REPORT_WARNING_BIT_EXT |
            VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static void main(String... args){
        Config config = null;
        Instance instance = new Instance(config);
        DebugCallback debugCallback = new DebugCallback(instance, config);
        Window window = new Window(config, instance);
        SwapChainSupportDetails swapChainSupportDetails = new SwapChainSupportDetails(window);
        QueueFamilyIndices queueFamilyIndices = new QueueFamilyIndices();
        PhysicalDevice physicalDevice = new PhysicalDevice(instance, swapChainSupportDetails, queueFamilyIndices, window);
        Device device = new Device(physicalDevice, queueFamilyIndices, config);
        EngineTask vulkanTask = new VulkanTask(instance, debugCallback, window, physicalDevice, device);

        System.out.println("Starting");
        vulkanTask.init();
        System.out.println("Running");
        while (!glfwWindowShouldClose(window.get())) {
            vulkanTask.update(10);
        }
        System.out.println("Closing");
        vulkanTask.close();

    }
}
