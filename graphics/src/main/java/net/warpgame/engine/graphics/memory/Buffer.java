package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VkUtil;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.vulkan.VkBufferCopy;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.lwjgl.vulkan.VkCommandBuffer;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.util.vma.Vma.*;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class Buffer implements Destroyable {
    private long buffer;

    private long allocation;

    private Allocator allocator;

    public Buffer(long size, int usage, int flags, int memeUsage, Allocator allocator){
        this.allocator = allocator;
        createBuffer(size, usage, flags, memeUsage);
    }

    @Override
    public void destroy() {
        vmaDestroyBuffer(allocator.get(), buffer, allocation);
    }

    public static void copyBuffer(ByteBuffer src, Buffer dst, long bytes){
        PointerBuffer pData = BufferUtils.createPointerBuffer(1);
        int err = vmaMapMemory(dst.getAllocator().get(), dst.getAllocation(), pData);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to map destination buffer memory", err);
        }
        long data = pData.get();
        memCopy(memAddress(src), data, bytes);
        vmaUnmapMemory(dst.getAllocator().get(), dst.getAllocation());
    }

    public static Fence copyBuffer(Buffer src, Buffer dst, long bytes, CommandPool commandPool){
        VkCommandBuffer commandBuffer = commandPool.beginSingleTimeCommands();

        VkBufferCopy.Buffer copyRegion = VkBufferCopy.create(1)
                .srcOffset(0)
                .dstOffset(0)
                .size(bytes);
        vkCmdCopyBuffer(commandBuffer, src.get(), dst.get(), copyRegion);

        return commandPool.endSingleTimeCommands(commandBuffer);
    }

    public static Fence copyBuffer(Buffer src, Image dst, int width, int height, CommandPool commandPool){
        VkCommandBuffer commandBuffer = commandPool.beginSingleTimeCommands();

        VkBufferImageCopy.Buffer region = VkBufferImageCopy.create(1)
                .bufferOffset(0)
                .bufferRowLength(0)
                .bufferImageHeight(0);

        region.imageSubresource()
                .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                .mipLevel(0)
                .baseArrayLayer(0)
                .layerCount(1);

        region.imageOffset().set(0,0,0);
        region.imageExtent().set(width, height, 1);

        vkCmdCopyBufferToImage(commandBuffer, src.get(), dst.get(), VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, region);

        return commandPool.endSingleTimeCommands(commandBuffer);
    }

    private void createBuffer(long size, int usage, int flags, int memUsage) {
        VkBufferCreateInfo bufferInfo = VkBufferCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
                .pNext(NULL)
                .size(size)
                .usage(usage)
                .flags(0)
                .sharingMode(VK_SHARING_MODE_EXCLUSIVE);

        VmaAllocationCreateInfo allocInfo = VmaAllocationCreateInfo.create()
                .usage(VkUtil.fixVmaMemoryUsage(memUsage))
                .requiredFlags(flags);

        LongBuffer pBuffer = BufferUtils.createLongBuffer(1);
        PointerBuffer pAllocation = BufferUtils.createPointerBuffer(1);
        int err = vmaCreateBuffer(allocator.get(), bufferInfo, allocInfo, pBuffer, pAllocation, null);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create buffer", err);
        }
        buffer = pBuffer.get();
        allocation = pAllocation.get();
    }

    private Allocator getAllocator() {
        return allocator;
    }

    public long get(){
        return buffer;
    }

    public long getAllocation() {
        return allocation;
    }
}
