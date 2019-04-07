package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.queue.QueueFamilyIndices;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

import java.nio.FloatBuffer;

import static net.warpgame.engine.graphics.ZerviceBypass.*;
import static net.warpgame.engine.graphics.utility.VKUtil.translateVulkanResult;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

@Service
public class Device extends CreateAndDestroy {
    private VkDevice device;

    private PhysicalDevice physicalDevice;
    private QueueFamilyIndices indices;
    private Config config;

    public Device(PhysicalDevice physicalDevice, QueueFamilyIndices indices, Config config) {
        this.physicalDevice = physicalDevice;
        this.indices = indices;
        this.config = config;
    }

    @Override
    public void create() {
        FloatBuffer pQueuePriorities = BufferUtils.createFloatBuffer(1).put(1.0f);
        pQueuePriorities.flip();

        VkDeviceQueueCreateInfo.Buffer pQueueCreateInfo = VkDeviceQueueCreateInfo.create(indices.number()).put(
                VkDeviceQueueCreateInfo.create()
                        .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                        .queueFamilyIndex(indices.getGraphicsFamily())
                        .pQueuePriorities(pQueuePriorities)
        );
        if(indices.number() > 1) {
            pQueueCreateInfo.put(
                    VkDeviceQueueCreateInfo.create()
                            .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                            .queueFamilyIndex(indices.getPresentFamily())
                            .pQueuePriorities(pQueuePriorities)
            );
        }
        pQueueCreateInfo.flip();

        PointerBuffer ppExtensionNames = BufferUtils.createPointerBuffer(DEVICE_EXTENSIONS.length);
        for (String extension : DEVICE_EXTENSIONS) {
            ppExtensionNames.put(memUTF8(extension));
        }
        ppExtensionNames.flip();

        VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.create();
        VkDeviceCreateInfo pCreateInfo = VkDeviceCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                .pNext(NULL)
                .pQueueCreateInfos(pQueueCreateInfo)
                .pEnabledFeatures(deviceFeatures)
                .ppEnabledExtensionNames(ppExtensionNames);

        if (ENABLE_VALIDATION_LAYERS) {
            PointerBuffer ppValidationLayers = BufferUtils.createPointerBuffer(VALIDATION_LAYERS.length);
            for (String layer : VALIDATION_LAYERS) {
                ppValidationLayers.put(memUTF8(layer));
            }
            pCreateInfo.ppEnabledLayerNames(ppValidationLayers.flip());
        }

        PointerBuffer pDevice = BufferUtils.createPointerBuffer(1);
        int err = vkCreateDevice(physicalDevice.get(), pCreateInfo, null, pDevice);
        if (err != VK_SUCCESS) {
            throw new AssertionError("Failed to create device: " + translateVulkanResult(err));
        }
        device = new VkDevice(pDevice.get(0), physicalDevice.get(), pCreateInfo);
    }

    @Override
    public void destroy() {
        vkDestroyDevice(device, null);
    }
}
