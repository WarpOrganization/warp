package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.PresentationQueue;
import net.warpgame.engine.graphics.command.QueueFamilyIndices;
import net.warpgame.engine.graphics.core.*;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.pipeline.GraphicsPipeline;
import net.warpgame.engine.graphics.pipeline.RenderPass;
import net.warpgame.engine.graphics.window.SwapChain;
import net.warpgame.engine.graphics.window.Window;

import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.KHRGetMemoryRequirements2.VK_KHR_GET_MEMORY_REQUIREMENTS_2_EXTENSION_NAME;
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
    public static final String[] DEVICE_EXTENSIONS = {VK_KHR_SWAPCHAIN_EXTENSION_NAME, VK_KHR_GET_MEMORY_REQUIREMENTS_2_EXTENSION_NAME};
    public static int DEBUG_REPORT =
            VK_DEBUG_REPORT_ERROR_BIT_EXT |
            VK_DEBUG_REPORT_WARNING_BIT_EXT |
            VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static boolean run = true;

    public static void main(String... args){
        Config config = null;

        Instance instance = new Instance(config);
        DebugCallback debugCallback = new DebugCallback(instance, config);
        Window window = new Window(config, instance);
        QueueFamilyIndices queueFamilyIndices = new QueueFamilyIndices();
        PhysicalDevice physicalDevice = new PhysicalDevice(instance, queueFamilyIndices, window);
        Device device = new Device(physicalDevice, queueFamilyIndices, config);
        Allocator allocator = new Allocator(instance, physicalDevice, device);
        GraphicsQueue graphicsQueue = new GraphicsQueue(device, queueFamilyIndices);
        PresentationQueue presentationQueue = new PresentationQueue(device, queueFamilyIndices);
        InstanceManager instanceManager = new InstanceManager(instance, debugCallback, window, physicalDevice, device, allocator, graphicsQueue, presentationQueue);

        SwapChain swapChain = new SwapChain(physicalDevice, device, window, queueFamilyIndices);
        RenderPass renderPass = new RenderPass(physicalDevice, device, allocator, swapChain);
        GraphicsPipeline graphicsPipeline = new GraphicsPipeline(device, swapChain, renderPass);

        EngineTask vulkanTask = new VulkanTask(instanceManager, swapChain, renderPass, graphicsPipeline);

        System.out.println("Starting");
        vulkanTask.init();
        System.out.println("Running");
        while (run) {
            vulkanTask.update(10);
            run = false;
        }
        System.out.println("Closing");
        vulkanTask.close();

    }
}
