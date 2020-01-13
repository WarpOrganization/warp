package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkDebugReportCallbackCreateInfoEXT;
import org.lwjgl.vulkan.VkDebugReportCallbackEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;

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
    private static final Logger logger = LoggerFactory.getLogger(DebugCallback.class);
    private static final int DEBUG_REPORT =
            VK_DEBUG_REPORT_ERROR_BIT_EXT |
                    VK_DEBUG_REPORT_WARNING_BIT_EXT |
                    VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT;

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
                String decodedMessage = VkDebugReportCallbackEXT.getString(pMessage);
                int bit = 1;
                while ((flags >>= 1)>0)
                    bit <<= 1;
                switch (bit) {
                    case VK_DEBUG_REPORT_DEBUG_BIT_EXT:
                        logger.debug(decodedMessage);
                        break;
                    case VK_DEBUG_REPORT_ERROR_BIT_EXT:
                        logger.error(decodedMessage);
                        break;
                    case VK_DEBUG_REPORT_PERFORMANCE_WARNING_BIT_EXT:
                    case VK_DEBUG_REPORT_WARNING_BIT_EXT:
                        logger.warn(decodedMessage);
                        break;
                    case VK_DEBUG_REPORT_INFORMATION_BIT_EXT:
                        logger.info(decodedMessage);
                        break;
                    default:
                        logger.error("Unexpected value: " + bit);
                }
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
}
