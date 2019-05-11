package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VkUtil;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.vulkan.VkBufferCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.util.vma.Vma.vmaCreateBuffer;
import static org.lwjgl.util.vma.Vma.vmaDestroyBuffer;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class Buffer implements Destroyable {
    private long buffer;

    private long allocation;

    private Allocator allocator;

    Buffer(long size, int usage, int flags, int memeUsage, Allocator allocator){
        this.allocator = allocator;
        createBuffer(size, usage, flags, memeUsage);
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

    @Override
    public void destroy() {
        vmaDestroyBuffer(allocator.get(), buffer, allocation);
    }
}
