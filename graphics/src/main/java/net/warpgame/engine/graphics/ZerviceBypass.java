package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.config.Config;

import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME;

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
    public static int DEBUG_REPORT = VK_DEBUG_REPORT_ERROR_BIT_EXT | VK_DEBUG_REPORT_WARNING_BIT_EXT | VK_DEBUG_REPORT_INFORMATION_BIT_EXT;

    public static void main(String... args){
        Config config = null;
        Instance instance = new Instance(config);
        DebugCallback debugCallback = new DebugCallback(instance, config);
        VulkanTask vulkanTask = new VulkanTask(instance, debugCallback);

        System.out.println("Starting");
        vulkanTask.onInit();
        System.out.println("Running");
        while (true) {
            break;
        }
        System.out.println("Closing");
        vulkanTask.onClose();

    }
}
