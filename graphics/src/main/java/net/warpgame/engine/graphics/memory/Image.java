package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VkUtil;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkImageCreateInfo;
import org.lwjgl.vulkan.VkImageMemoryBarrier;

import java.nio.LongBuffer;

import static org.lwjgl.util.vma.Vma.vmaCreateImage;
import static org.lwjgl.util.vma.Vma.vmaDestroyImage;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 12.04.2019
 */
public class Image implements CreateAndDestroy {
    private long image;

    private long allocation;
    private int format;
    private int mipLevels;

    private Device device;
    private Allocator allocator;

    public Image(long image, int format, int mipLevels, Device device) {
        assign(image, -1, format, mipLevels, device, null);
    }

    public Image(int width, int height, int format, int mipLevels, int imageUsage, int memoryUsage, Allocator allocator) {
        VkImageCreateInfo imageCreateInfo = VkImageCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO)
                .imageType(VK_IMAGE_TYPE_2D)
                .mipLevels(mipLevels)
                .arrayLayers(1)
                .format(format)
                .tiling(VK_IMAGE_TILING_OPTIMAL)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .usage(imageUsage)
                .sharingMode(VK_SHARING_MODE_EXCLUSIVE)
                .samples(VK_SAMPLE_COUNT_1_BIT)
                .flags(0);

        imageCreateInfo.extent()
                .width(width)
                .height(height)
                .depth(1);

        VmaAllocationCreateInfo allocInfo = VmaAllocationCreateInfo.create()
                .usage(VkUtil.fixVmaMemoryUsage(memoryUsage));

        LongBuffer pImage = BufferUtils.createLongBuffer(1);
        PointerBuffer pAllocation = BufferUtils.createPointerBuffer(1);
        int err = vmaCreateImage(allocator.get(), imageCreateInfo, allocInfo, pImage, pAllocation, null);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create image", err);
        }

        assign(pImage.get(0), pAllocation.get(0), format, mipLevels, null, allocator);
    }

    public void transitionLayout(int oldLayout, int newLayout, CommandPool commandPool) {
        VkCommandBuffer commandBuffer = commandPool.beginSingleTimeCommands();

        VkImageMemoryBarrier.Buffer barrier = VkImageMemoryBarrier.create(1)
                .sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
                .oldLayout(oldLayout)
                .newLayout(newLayout)
                .srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED)
                .dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED)
                .image(image);

        int aspectMask;
        if (newLayout == VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL) {
            aspectMask = VK_IMAGE_ASPECT_DEPTH_BIT;

            if (hasStencilComponent(format)) {
                aspectMask |= VK_IMAGE_ASPECT_STENCIL_BIT;
            }
        } else {
            aspectMask = VK_IMAGE_ASPECT_COLOR_BIT;
        }

        barrier.subresourceRange()
                .aspectMask(aspectMask)
                .baseMipLevel(0)
                .levelCount(mipLevels)
                .baseArrayLayer(0)
                .layerCount(1);

        barrier.srcAccessMask(0).dstAccessMask(0);

        int sourceStage;
        int destinationStage;

        if (oldLayout == VK_IMAGE_LAYOUT_UNDEFINED && newLayout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL) {
            barrier.srcAccessMask(0).dstAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);

            sourceStage = VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
            destinationStage = VK_PIPELINE_STAGE_TRANSFER_BIT;
        } else if (oldLayout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL && newLayout == VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL) {
            barrier.srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT).dstAccessMask(VK_ACCESS_SHADER_READ_BIT);

            sourceStage = VK_PIPELINE_STAGE_TRANSFER_BIT;
            destinationStage = VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT;
        } else if (oldLayout == VK_IMAGE_LAYOUT_UNDEFINED && newLayout == VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL) {
            barrier.srcAccessMask(0).dstAccessMask(VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT | VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT);

            sourceStage = VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
            destinationStage = VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT;
        } else {
            throw new RuntimeException("Unsupported layout transition");
        }

        vkCmdPipelineBarrier(commandBuffer, sourceStage, destinationStage, 0, null, null, barrier);

        commandPool.endSingleTimeCommands(commandBuffer);
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        if(allocation == -1) {
            vkDestroyImage(device.get(), image, null);
        }else{
            vmaDestroyImage(allocator.get(), image, allocation);
        }
    }

    private void assign(long image, long allocation, int imageFormat, int mipLevels, Device device, Allocator allocator) {
        this.image = image;
        this.allocation = allocation;
        this.format = imageFormat;
        this.mipLevels = mipLevels;
        this.device = device;
        this.allocator = allocator;
    }

    private boolean hasStencilComponent(long format) {
        return format == VK_FORMAT_D32_SFLOAT_S8_UINT || format == VK_FORMAT_D24_UNORM_S8_UINT;
    }

    public long get() {
        return image;
    }

    public int getFormat() {
        return format;
    }

    public int getMipLevels() {
        return mipLevels;
    }
}
