package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;

import static org.lwjgl.vulkan.VK10.vkDestroyImage;

/**
 * @author MarconZet
 * Created 12.04.2019
 */
public class Image implements CreateAndDestroy {
    private long image;

    private int imageFormat;
    private int mipLevels;

    private Device device;

    public Image(long image, int imageFormat, int mipLevels, Device device) {
        this.image = image;
        this.imageFormat = imageFormat;
        this.mipLevels = mipLevels;
        this.device = device;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        vkDestroyImage(device.get(), image, null);
    }

    public long get() {
        return image;
    }

    public int getImageFormat() {
        return imageFormat;
    }

    public int getMipLevels() {
        return mipLevels;
    }
}
