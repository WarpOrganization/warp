package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkImageViewCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 12.04.2019
 */
public class ImageView implements CreateAndDestroy {
    private long imageView;

    private int aspectFlags;

    private Device device;
    private Image image;

    public ImageView(Image image, int aspectFlags, Device device) {
        this.image = image;
        this.aspectFlags = aspectFlags;
        this.device = device;
        create();
    }

    @Override
    public void create() {
        VkImageViewCreateInfo viewInfo = VkImageViewCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
                .pNext(VK_NULL_HANDLE)
                .flags(0)
                .image(image.get())
                .viewType(VK_IMAGE_VIEW_TYPE_2D)
                .format(image.getFormat());
        viewInfo.subresourceRange()
                .aspectMask(aspectFlags)
                .baseMipLevel(0)
                .levelCount(image.getMipLevels())
                .baseArrayLayer(0)
                .layerCount(1);

        LongBuffer pointer = BufferUtils.createLongBuffer(1);
        int err = vkCreateImageView(device.get(), viewInfo, null, pointer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create texture image view", err);
        }
        imageView = pointer.get(0);
    }

    @Override
    public void destroy() {
        vkDestroyImageView(device.get(), imageView, null);
    }

}
