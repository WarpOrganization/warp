package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.04.2019
 */
public abstract class CommandPool implements CreateAndDestroy {
    private long commandPool;

    private Device device;
    private Queue queue;

    public CommandPool(Device device, Queue queue) {
        this.device = device;
        this.queue = queue;
        create();
    }

    @Override
    public void create() {
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

    protected abstract int getFlags();

    public long get(){
        return commandPool;
    }
}
