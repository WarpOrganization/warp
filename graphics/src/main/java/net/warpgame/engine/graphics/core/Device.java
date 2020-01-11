package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.queue.QueueManager;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

import java.nio.FloatBuffer;

import static net.warpgame.engine.graphics.GraphicsConfig.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memUTF8;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

@Service
@Profile("graphics")
public class Device implements CreateAndDestroy {
    private VkDevice device;

    private QueueManager queueManager;
    private PhysicalDevice physicalDevice;
    private Config config;

    public Device(PhysicalDevice physicalDevice, QueueManager queueManager, Config config) {
        this.physicalDevice = physicalDevice;
        this.queueManager = queueManager;
        this.config = config;
    }

    @Override
    public boolean isCreated() {
        return device != null;
    }

    @Override
    public void create() {
        FloatBuffer pQueuePriorities = BufferUtils.createFloatBuffer(1).put(1.0f);
        pQueuePriorities.flip();

        VkDeviceQueueCreateInfo.Buffer pQueueCreateInfo = VkDeviceQueueCreateInfo.create(queueManager.queueNumber()).put(
                VkDeviceQueueCreateInfo.create()
                        .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                        .queueFamilyIndex(queueManager.getGraphicsFamily())
                        .pQueuePriorities(pQueuePriorities)
        );
        if(queueManager.isPresentationUnique()) {
            pQueueCreateInfo.put(
                    VkDeviceQueueCreateInfo.create()
                            .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                            .queueFamilyIndex(queueManager.getPresentFamily())
                            .pQueuePriorities(pQueuePriorities)
            );
        }
        if(queueManager.isTransportUnique()) {
            pQueueCreateInfo.put(
                    VkDeviceQueueCreateInfo.create()
                            .sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                            .queueFamilyIndex(queueManager.getTransportFamily())
                            .pQueuePriorities(pQueuePriorities)
            );
        }
        pQueueCreateInfo.flip();

        PointerBuffer ppExtensionNames = BufferUtils.createPointerBuffer(DEVICE_EXTENSIONS.length);
        for (String extension : DEVICE_EXTENSIONS) {
            ppExtensionNames.put(memUTF8(extension));
        }
        ppExtensionNames.flip();

        VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.create()
                .samplerAnisotropy(true);
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
            throw new VulkanAssertionError("Failed to create device", err);
        }
        device = new VkDevice(pDevice.get(0), physicalDevice.get(), pCreateInfo);
    }

    @Override
    public void destroy() {
        vkDestroyDevice(device, null);
    }

    public VkDevice get() {
        return device;
    }
}
