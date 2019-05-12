package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VkUtil;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.vulkan.*;

import java.nio.LongBuffer;

import static org.lwjgl.util.vma.Vma.vmaCreateImage;
import static org.lwjgl.util.vma.Vma.vmaDestroyImage;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 12.04.2019
 */
public class Image implements Destroyable {
    private long image;

    private int width;
    private int height;
    private int format;
    private int layout;
    private int mipLevels;
    private long allocation;

    private Device device;
    private Allocator allocator;

    public Image(long image, int format, int mipLevels, Device device) {
        assign(image, -1, -1, -1, format, -1, mipLevels, device, null);
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

        assign(pImage.get(0), width, height, pAllocation.get(0), format, VK_IMAGE_LAYOUT_UNDEFINED, mipLevels, null, allocator);
    }

    public Fence transitionLayout(int newLayout, CommandPool commandPool) {
        VkCommandBuffer commandBuffer = commandPool.beginSingleTimeCommands();

        VkImageMemoryBarrier.Buffer barrier = VkImageMemoryBarrier.create(1)
                .sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
                .oldLayout(layout)
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

        if (layout == VK_IMAGE_LAYOUT_UNDEFINED && newLayout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL) {
            barrier.srcAccessMask(0).dstAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT);

            sourceStage = VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
            destinationStage = VK_PIPELINE_STAGE_TRANSFER_BIT;
        } else if (layout == VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL && newLayout == VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL) {
            barrier.srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT).dstAccessMask(VK_ACCESS_SHADER_READ_BIT);

            sourceStage = VK_PIPELINE_STAGE_TRANSFER_BIT;
            destinationStage = VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT;
        } else if (layout == VK_IMAGE_LAYOUT_UNDEFINED && newLayout == VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL) {
            barrier.srcAccessMask(0).dstAccessMask(VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT | VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT);

            sourceStage = VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
            destinationStage = VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT;
        } else {
            throw new RuntimeException("Unsupported layout transition");
        }

        vkCmdPipelineBarrier(commandBuffer, sourceStage, destinationStage, 0, null, null, barrier);
        layout = newLayout;
        return commandPool.endSingleTimeCommands(commandBuffer);
    }

    public Fence generateMipmaps(CommandPool commandPool){
        /*VkFormatProperties formatProperties = VkFormatProperties.create();
        vkGetPhysicalDeviceFormatProperties(physicalDevice, format, formatProperties);
        if (!((formatProperties.optimalTilingFeatures() & VK_FORMAT_FEATURE_SAMPLED_IMAGE_FILTER_LINEAR_BIT)>0)) {
            throw new RuntimeException("Texture image format does not support linear blitting");//TODO move somewhere
        }*/


        VkCommandBuffer commandBuffer = commandPool.beginSingleTimeCommands();

        VkImageMemoryBarrier.Buffer barrier = VkImageMemoryBarrier.create(1)
                .sType(VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER)
                .image(image)
                .srcQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED)
                .dstQueueFamilyIndex(VK_QUEUE_FAMILY_IGNORED);
        barrier.subresourceRange()
                .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                .baseArrayLayer(0)
                .layerCount(1)
                .levelCount(1);

        int mipWidth = width;
        int mipHeight = height;

        for (int i = 1; i < mipLevels; i++) {
            barrier.subresourceRange().baseMipLevel(i-1);
            barrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
                    .newLayout(VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL)
                    .srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT)
                    .dstAccessMask(VK_ACCESS_TRANSFER_READ_BIT);

            vkCmdPipelineBarrier(commandBuffer,
                    VK_PIPELINE_STAGE_TRANSFER_BIT,
                    VK_PIPELINE_STAGE_TRANSFER_BIT,
                    0,
                    null,
                    null,
                    barrier);

            VkOffset3D.Buffer srcOffsets = VkOffset3D.create(2);
            srcOffsets.get(0).set(0, 0, 0);
            srcOffsets.get(1).set(mipWidth, mipHeight, 1);
            VkOffset3D.Buffer dstOffsets = VkOffset3D.create(2);
            dstOffsets.get(0).set(0,0,0);
            dstOffsets.get(1).set(mipWidth > 1 ? mipWidth / 2 : 1, mipHeight > 1 ? mipHeight / 2 : 1, 1);
            VkImageSubresourceLayers subresource = VkImageSubresourceLayers.create()
                    .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
                    .mipLevel(i - 1)
                    .baseArrayLayer(0)
                    .layerCount(1);
            VkImageBlit.Buffer blit = VkImageBlit.create(1)
                    .srcOffsets(srcOffsets)
                    .srcSubresource(subresource)
                    .dstOffsets(dstOffsets)
                    .dstSubresource(subresource.mipLevel(i));

            vkCmdBlitImage(commandBuffer,
                    image, VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL,
                    image, VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL,
                    blit,
                    VK_FILTER_LINEAR);

            barrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_SRC_OPTIMAL)
                    .newLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
                    .srcAccessMask(VK_ACCESS_TRANSFER_READ_BIT)
                    .dstAccessMask(VK_ACCESS_SHADER_READ_BIT);

            vkCmdPipelineBarrier(commandBuffer,
                    VK_PIPELINE_STAGE_TRANSFER_BIT,
                    VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT,
                    0,
                    null,
                    null,
                    barrier);

            if (mipWidth > 1) mipWidth /= 2;
            if (mipHeight > 1) mipHeight /= 2;
        }

        barrier.subresourceRange().baseMipLevel(mipLevels-1);
        barrier.oldLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL)
                .newLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
                .srcAccessMask(VK_ACCESS_TRANSFER_WRITE_BIT)
                .dstAccessMask(VK_ACCESS_SHADER_READ_BIT);

        vkCmdPipelineBarrier(commandBuffer,
                VK_PIPELINE_STAGE_TRANSFER_BIT,
                VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT,
                0,
                null,
                null,
                barrier);

        return commandPool.endSingleTimeCommands(commandBuffer);
    }

    @Override
    public void destroy() {
        if(allocation != -1) {
            vmaDestroyImage(allocator.get(), image, allocation);
        }else{
            vkDestroyImage(device.get(), image, null);
        }
    }

    private void assign(long image, int width, int height, long allocation, int imageFormat, int layout, int mipLevels, Device device, Allocator allocator) {
        this.image = image;
        this.width = width;
        this.height = height;
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
