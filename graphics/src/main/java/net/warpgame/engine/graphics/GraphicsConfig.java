package net.warpgame.engine.graphics;

import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.KHRGetMemoryRequirements2.VK_KHR_GET_MEMORY_REQUIREMENTS_2_EXTENSION_NAME;
import static org.lwjgl.vulkan.KHRSurface.VK_KHR_SURFACE_EXTENSION_NAME;
import static org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME;

/**
 * @author MarconZet
 * Created 05.04.2019
 */
public class GraphicsConfig {
    //TODO this staff should come form config, and not be hardcoded
    public static final boolean ENABLE_VALIDATION_LAYERS = true;
    public static final String[] VALIDATION_LAYERS = {"VK_LAYER_KHRONOS_validation"};
    public static final String[] VALIDATION_LAYERS_INSTANCE_EXTENSIONS = {VK_EXT_DEBUG_REPORT_EXTENSION_NAME};
    public static final String[] INSTANCE_EXTENSIONS = {VK_KHR_SURFACE_EXTENSION_NAME};
    public static final String[] DEVICE_EXTENSIONS = {VK_KHR_SWAPCHAIN_EXTENSION_NAME, VK_KHR_GET_MEMORY_REQUIREMENTS_2_EXTENSION_NAME};
    public static int DEBUG_REPORT =
                    VK_DEBUG_REPORT_ERROR_BIT_EXT |
                    VK_DEBUG_REPORT_WARNING_BIT_EXT |
                    VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
}
