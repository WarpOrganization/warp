package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkDebugReportCallbackCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugReportCallbackEXT;

import java.nio.LongBuffer;

import static net.warpgame.engine.graphics.ZerviceBypass.DEBUG_REPORT;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * @author MarconZet
 * Created 05.04.2019
 */
@Service
@Profile("graphics")
public class DebugCallback implements CreateAndDestroy {
    private long debugCallbackHandle;

    private Instance instance;
    private Config config;

    public DebugCallback(Instance instance, Config config) {
        this.instance = instance;
        this.config = config;
    }

    @Override
    public void create() {
        VkDebugReportCallbackEXT debugCallback = new VkDebugReportCallbackEXT() {
            public int invoke(int flags, int objectType, long object, long location, int messageCode, long pLayerPrefix, long pMessage, long pUserData) {
                String decodedMessage = String.format("%s %s", translateDebugFlags(flags), VkDebugReportCallbackEXT.getString(pMessage));
                if(flags >= VK_DEBUG_REPORT_WARNING_BIT_EXT)
                    System.err.println(decodedMessage);
                else
                    System.out.println(decodedMessage);
                return 0;
            }
        };
        debugCallbackHandle = setupDebugging(instance, DEBUG_REPORT, debugCallback);
    }

    @Override
    public void destroy() {
        vkDestroyDebugReportCallbackEXT(instance.get(), debugCallbackHandle, null);
    }

    private long setupDebugging(Instance instance, int flags, VkDebugReportCallbackEXT callback) {
        VkDebugReportCallbackCreateInfoEXT dbgCreateInfo = VkDebugReportCallbackCreateInfoEXT.create()
                .sType(VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT)
                .pNext(NULL)
                .pfnCallback(callback)
                .pUserData(NULL)
                .flags(flags);
        LongBuffer pCallback = BufferUtils.createLongBuffer(1);
        int err = vkCreateDebugReportCallbackEXT(instance.get(), dbgCreateInfo, null, pCallback);
        long callbackHandle = pCallback.get(0);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create VkInstance", err);
        }
        return callbackHandle;
    }

    private static String translateDebugFlags(int flags){
        switch (flags){
            case VK_DEBUG_REPORT_INFORMATION_BIT_EXT:
                return "INFO";
            case VK_DEBUG_REPORT_WARNING_BIT_EXT:
                return "WARN";
            case VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT:
                return "PERFORMANCE WARN";
            case VK_DEBUG_REPORT_ERROR_BIT_EXT:
                return "ERROR";
            case VK_DEBUG_REPORT_DEBUG_BIT_EXT:
                return "DEBUG";
            default:
                return String.format("Unknown [%d]", flags);


        }
    }
}
