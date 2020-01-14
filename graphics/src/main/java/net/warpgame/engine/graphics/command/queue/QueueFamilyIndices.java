package net.warpgame.engine.graphics.command.queue;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.window.Window;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkQueueFamilyProperties;

import java.nio.IntBuffer;
import java.util.stream.Stream;

import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 09.09.2018
 */

@Service
@Profile("graphics")
public class QueueFamilyIndices {
    private int transportFamily = -1;

    private QueueFamilyProperties[] queueFamiliesProperties;

    public QueueFamilyIndices() {

    }

    public void getQueueFamilyIndices(PhysicalDevice physicalDevice, Window window) {
        long surface = window.getSurface();
        IntBuffer pQueueFamilyCount = BufferUtils.createIntBuffer(1);
        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice.get(), pQueueFamilyCount, null);
        int queueFamilyCount = pQueueFamilyCount.get(0);

        VkQueueFamilyProperties.Buffer pQueueFamilies = VkQueueFamilyProperties.create(queueFamilyCount);
        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice.get(), pQueueFamilyCount, pQueueFamilies);

        queueFamiliesProperties = new QueueFamilyProperties[queueFamilyCount];

        int i = 0;
        while (pQueueFamilies.hasRemaining()) {
            VkQueueFamilyProperties queueFamily = pQueueFamilies.get();
            IntBuffer pSupport = BufferUtils.createIntBuffer(1);
            vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice.get(), i, surface, pSupport);
            boolean support = pSupport.get() == VK_TRUE;
            queueFamiliesProperties[i] = new QueueFamilyProperties(i, queueFamily.queueFlags(), queueFamily.queueCount(), support);
            i++;
        }


    }

    public boolean isGoodEnough() {
        return Stream.of(queueFamiliesProperties).anyMatch(QueueFamilyProperties::isPresentSupport) &&
                Stream.of(queueFamiliesProperties).anyMatch(QueueFamilyProperties::isGraphicsSupport) &&
                Stream.of(queueFamiliesProperties).anyMatch(QueueFamilyProperties::isTransferSupport);
    }

    public QueueFamilyProperties[] getQueueFamiliesProperties() {
        return queueFamiliesProperties;
    }
}