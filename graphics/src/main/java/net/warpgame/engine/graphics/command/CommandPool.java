package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.*;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.04.2019
 */
public abstract class CommandPool implements Destroyable {
    private long commandPool;

    private Device device;
    private Queue queue;

    public CommandPool(Device device, Queue queue) {
        this.device = device;
        this.queue = queue;
        create();
    }

    private void create() {
        VkCommandPoolCreateInfo createInfo = VkCommandPoolCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                .pNext(VK_NULL_HANDLE)
                .queueFamilyIndex(queue.getFamily())
                .flags(getFlags());


        LongBuffer pCommandPoll = BufferUtils.createLongBuffer(1);
        int err = vkCreateCommandPool(device.get(), createInfo, null, pCommandPoll);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create command pool", err);
        }
        commandPool = pCommandPoll.get();
    }

    @Override
    public void destroy() {
        vkDestroyCommandPool(device.get(), commandPool, null);
    }

    public VkCommandBuffer[] createCommandBuffer(int n){
        VkCommandBuffer[] commandBuffers = new VkCommandBuffer[n];
        VkCommandBufferAllocateInfo allocateInfo = VkCommandBufferAllocateInfo.create()
                .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
                .commandPool(commandPool)
                .level(VK_COMMAND_BUFFER_LEVEL_PRIMARY)
                .commandBufferCount(commandBuffers.length);

        PointerBuffer pointerBuffer = BufferUtils.createPointerBuffer(commandBuffers.length);
        int err = vkAllocateCommandBuffers(device.get(), allocateInfo, pointerBuffer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to allocate command buffers", err);
        }

        for (int i = 0; i < commandBuffers.length; i++) {
            long l = pointerBuffer.get();
            commandBuffers[i] = new VkCommandBuffer(l, device.get());
        }
        return commandBuffers;
    }

    public VkCommandBuffer createCommandBuffer(){
        return createCommandBuffer(1)[0];
    }

    public VkCommandBuffer beginSingleTimeCommands() {
        VkCommandBuffer commandBuffer = this.createCommandBuffer();

        VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.create()
                .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
                .flags(VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);

        int err = vkBeginCommandBuffer(commandBuffer, beginInfo);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to begin single time command buffer", err);
        }
        return commandBuffer;
    }

    public Fence endSingleTimeCommands(VkCommandBuffer commandBuffer) {
        vkEndCommandBuffer(commandBuffer);

        PointerBuffer pointerBuffer = BufferUtils.createPointerBuffer(1).put(0, commandBuffer);
        VkSubmitInfo submitInfo = VkSubmitInfo.create()
                .sType(VK_STRUCTURE_TYPE_SUBMIT_INFO)
                .pCommandBuffers(pointerBuffer);

        Fence fence = new Fence(device);
        vkQueueSubmit(queue.get(), submitInfo, fence.get());
        return fence.onDestroy(() -> freeCommandBuffer(commandBuffer));
    }

    public void freeCommandBuffer(VkCommandBuffer commandBuffer){
        PointerBuffer pointerBuffer = BufferUtils.createPointerBuffer(1).put(0, commandBuffer);
        vkFreeCommandBuffers(device.get(), commandPool, pointerBuffer);
    }

    protected abstract int getFlags();

    public long get(){
        return commandPool;
    }
}
