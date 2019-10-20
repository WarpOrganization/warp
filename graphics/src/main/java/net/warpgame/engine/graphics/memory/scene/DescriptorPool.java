package net.warpgame.engine.graphics.memory.scene;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.GraphicsConfig;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 19.10.2019
 */

@Service
public class DescriptorPool implements CreateAndDestroy {
    private long descriptorPool;

    private int[] types = new int[]{VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER, VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER};
    private int sets = GraphicsConfig.DESCRIPTOR_POOL_MAX_SETS;

    private Device device;
    private SwapChain swapChain;

    public DescriptorPool(Device device, SwapChain swapChain) {
        this.device = device;
        this.swapChain = swapChain;
    }

    @Override
    public void create() {
        VkDescriptorPoolSize.Buffer poolSize = VkDescriptorPoolSize.create(types.length);
        for (int i = 0; i < types.length; i++) {
            poolSize.get(i).type(types[i]).descriptorCount(sets * swapChain.getImages().length);
        }

        VkDescriptorPoolCreateInfo poolCreateInfo = VkDescriptorPoolCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO)
                .pPoolSizes(poolSize)
                .maxSets(sets * swapChain.getImages().length)
                .flags(0);

        LongBuffer pointer = BufferUtils.createLongBuffer(1);
        int err = vkCreateDescriptorPool(device.get(), poolCreateInfo, null, pointer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create descriptor pool", err);
        }
        descriptorPool = pointer.get(0);
    }

    @Override
    public void destroy() {
        vkDestroyDescriptorPool(device.get(), descriptorPool, null);
    }
}
