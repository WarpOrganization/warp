package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.QueueFamilyIndices;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.window.SwapChainSupportDetails;
import net.warpgame.engine.graphics.window.Window;
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
public class PhysicalDevice implements CreateAndDestroy {
    private VkPhysicalDevice physicalDevice;
    private VkPhysicalDeviceProperties deviceProperties;
    private VkPhysicalDeviceFeatures deviceFeatures;

    private Instance instance;
    private SwapChainSupportDetails swapChainSupportDetails;
    private QueueFamilyIndices indices;
    private Window window;

    public PhysicalDevice(Instance instance, SwapChainSupportDetails swapChainSupportDetails, QueueFamilyIndices indices, Window window) {
        this.instance = instance;
        this.swapChainSupportDetails = swapChainSupportDetails;
        this.indices = indices;
        this.window = window;
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
            if (isDeviceSuitable()) {
                return;
            }
        }
        throw new AssertionError("Failed to find a suitable GPU");
    }

    @Override
    public void destroy() {

    }

    private boolean isDeviceSuitable() {
        deviceProperties = VkPhysicalDeviceProperties.create();
        deviceFeatures = VkPhysicalDeviceFeatures.create();
        vkGetPhysicalDeviceProperties(physicalDevice, deviceProperties);
        vkGetPhysicalDeviceFeatures(physicalDevice, deviceFeatures);
        swapChainSupportDetails.acquireSupportDetails(this);
        if(swapChainSupportDetails.getFormats().sizeof() == 0){
            return false;
        }
        if(!swapChainSupportDetails.getPresentModes().hasRemaining()){
            return false;
        }
        indices.getQueueFamilyIndices(this, window);
        if(!indices.isComplete()){
            return false;
        }
        return deviceProperties.deviceType() == VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU;
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
