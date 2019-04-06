package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Service;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkPhysicalDevice;

import java.nio.IntBuffer;

import static net.warpgame.engine.graphics.utility.VKUtil.translateVulkanResult;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.vkEnumeratePhysicalDevices;

/**
 * @author MarconZet
 * Created 06.04.2019
 */

@Service
public class PhysicalDevice extends VkObject{
    private VkPhysicalDevice physicalDevice;

    private Instance instance;

    public PhysicalDevice(Instance instance) {
        this.instance = instance;
    }

    @Override
    public void create() {
        IntBuffer pPhysicalDeviceCount = BufferUtils.createIntBuffer(1);
        int err = vkEnumeratePhysicalDevices(instance.get(), pPhysicalDeviceCount, null);
        if (err != VK_SUCCESS) {
            throw new AssertionError("Failed to get number of physical devices: " + translateVulkanResult(err));
        }
        PointerBuffer pPhysicalDevices = BufferUtils.createPointerBuffer(pPhysicalDeviceCount.get(0));
        err = vkEnumeratePhysicalDevices(instance.get(), pPhysicalDeviceCount, pPhysicalDevices);
        long physicalDevice = pPhysicalDevices.get(0); //TODO getting the first physical device that pops up is not the most optimal strategy
        if (err != VK_SUCCESS) {
            throw new AssertionError("Failed to get physical devices: " + translateVulkanResult(err));
        }
        this.physicalDevice = new VkPhysicalDevice(physicalDevice, instance.get());
    }

    @Override
    public void destroy() {

    }

    public VkPhysicalDevice get(){
        return this.physicalDevice;
    }
}
