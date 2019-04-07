package net.warpgame.engine.graphics.queue;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.window.Window;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkQueueFamilyProperties;

import java.nio.IntBuffer;

import static org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 09.09.2018
 */

@Service
public class QueueFamilyIndices {
    private int graphicsFamily = -1;
    private int graphicsFamilyCount = -1;
    private int presentFamily = -1;
    private int presentFamilyCount = -1;

    public QueueFamilyIndices() {

    }

    public void getQueueFamilyIndices(PhysicalDevice physicalDevice, Window window) {
        long surface = window.getSurface();
        IntBuffer pQueueFamilyCount = BufferUtils.createIntBuffer(1);
        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice.get(), pQueueFamilyCount, null);
        int queueFamilyCount = pQueueFamilyCount.get(0);

        VkQueueFamilyProperties.Buffer pQueueFamilies = VkQueueFamilyProperties.create(queueFamilyCount);
        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice.get(), pQueueFamilyCount, pQueueFamilies);

        int i = 0;
        while (pQueueFamilies.hasRemaining()) {
            VkQueueFamilyProperties queueFamily = pQueueFamilies.get();
            IntBuffer pSupport = BufferUtils.createIntBuffer(1);
            vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice.get(), i, surface, pSupport);
            boolean support = pSupport.get() == VK_TRUE;
            if (queueFamily.queueCount() > 0 && (queueFamily.queueFlags() & VK_QUEUE_GRAPHICS_BIT)>0) {
                graphicsFamily = i;
                graphicsFamilyCount = queueFamily.queueCount();
            }

            if (queueFamily.queueCount() > 0 && support) {
                presentFamily = i;
                presentFamilyCount = queueFamily.queueCount();
            }

            if (isComplete()) {
                break;
            }

            i++;
        }
    }

    public boolean isComplete() {
        return graphicsFamily >= 0 && presentFamily >= 0;
    }

    public int getGraphicsFamily() {
        return graphicsFamily;
    }

    public int getPresentFamily() {
        return presentFamily;
    }

    public int getGraphicsFamilyCount() {
        return graphicsFamilyCount;
    }

    public int getPresentFamilyCount() {
        return presentFamilyCount;
    }
}