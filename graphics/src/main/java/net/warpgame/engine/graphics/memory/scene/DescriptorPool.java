package net.warpgame.engine.graphics.memory.scene;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.rendering.pipeline.GraphicsPipeline;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkDescriptorPoolCreateInfo;
import org.lwjgl.vulkan.VkDescriptorPoolSize;
import org.lwjgl.vulkan.VkDescriptorSetAllocateInfo;

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
    private int sets;
    private int swapChainImagesLength;

    private Device device;
    private SwapChain swapChain;
    private GraphicsPipeline graphicsPipeline;

    public DescriptorPool(Device device, SwapChain swapChain, GraphicsPipeline graphicsPipeline, Config config) {
        this.device = device;
        this.swapChain = swapChain;
        this.graphicsPipeline = graphicsPipeline;
        this.sets = config.getValue("graphics.vulkan.loading.maxObjects");
    }

    @Override
    public void create() {
        swapChainImagesLength = swapChain.getImages().length;
        VkDescriptorPoolSize.Buffer poolSize = VkDescriptorPoolSize.create(types.length);
        for (int i = 0; i < types.length; i++) {
            poolSize.get(i).type(types[i]).descriptorCount(sets * swapChainImagesLength);
        }

        VkDescriptorPoolCreateInfo poolCreateInfo = VkDescriptorPoolCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO)
                .pPoolSizes(poolSize)
                .maxSets(sets * swapChainImagesLength)
                .flags(0);

        LongBuffer pointer = BufferUtils.createLongBuffer(1);
        int err = vkCreateDescriptorPool(device.get(), poolCreateInfo, null, pointer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create descriptor pool", err);
        }
        descriptorPool = pointer.get(0);
    }

    public long[] getDescriptorSets(){
        LongBuffer layouts = BufferUtils.createLongBuffer(swapChainImagesLength);
        for (int i = 0; i < swapChainImagesLength; i++) {
            layouts.put(graphicsPipeline.getDescriptorSetLayout());
        }
        layouts.flip();
        VkDescriptorSetAllocateInfo allocateInfo = VkDescriptorSetAllocateInfo.create()
                .sType(VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO)
                .descriptorPool(descriptorPool)
                .pSetLayouts(layouts);


        LongBuffer array = BufferUtils.createLongBuffer(swapChainImagesLength);
        int err = vkAllocateDescriptorSets(device.get(), allocateInfo, array);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create descriptor sets", err);
        }
        long[] descriptorSets = new long[swapChainImagesLength];
        array.get(descriptorSets);
        return descriptorSets;
    }

    @Override
    public void destroy() {
        vkDestroyDescriptorPool(device.get(), descriptorPool, null);
    }

    public int getSwapChainImagesLength() {
        return swapChainImagesLength;
    }
}
