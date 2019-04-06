package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;

import java.nio.IntBuffer;

import static net.warpgame.engine.graphics.utility.VKUtil.translateVulkanResult;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK10.vkGetPhysicalDeviceFeatures;

/**
 * @author MarconZet
 * Created 06.04.2019
 */

@Service
public class PhysicalDevice extends CreateAndDestroy {
    private VkPhysicalDevice physicalDevice;
    private VkPhysicalDeviceProperties deviceProperties;
    private VkPhysicalDeviceFeatures deviceFeatures;

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
        int deviceCount = pPhysicalDeviceCount.get(0);
        if (deviceCount == 0) {
            throw new AssertionError("Failed to find GPUs with Vulkan support");
        }

        PointerBuffer pPhysicalDevices = BufferUtils.createPointerBuffer(deviceCount);
        err = vkEnumeratePhysicalDevices(instance.get(), pPhysicalDeviceCount, pPhysicalDevices);
        if (err != VK_SUCCESS) {
            throw new AssertionError("Failed to get physical devices: " + translateVulkanResult(err));
        }

        while(pPhysicalDevices.hasRemaining()) {
            physicalDevice = new VkPhysicalDevice(pPhysicalDevices.get(), instance.get());
            if (isDeviceSuitable(physicalDevice)) {
                return;
            }
        }
        throw new AssertionError("Failed to find a suitable GPU");
    }

    @Override
    public void destroy() {

    }

    private boolean isDeviceSuitable(VkPhysicalDevice physicalDevice) {
        deviceProperties = VkPhysicalDeviceProperties.create();
        deviceFeatures = VkPhysicalDeviceFeatures.create();
        vkGetPhysicalDeviceProperties(physicalDevice, deviceProperties);
        vkGetPhysicalDeviceFeatures(physicalDevice, deviceFeatures);
        return true;
    }

    public VkPhysicalDevice get(){
        return this.physicalDevice;
    }

    public VkPhysicalDeviceProperties getProperties() {
        return deviceProperties;
    }

    public VkPhysicalDeviceFeatures getFeatures() {
        return deviceFeatures;
    }
}
