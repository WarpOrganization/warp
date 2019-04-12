package net.warpgame.engine.graphics.image;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;

import static org.lwjgl.vulkan.VK10.vkDestroyImage;

/**
 * @author MarconZet
 * Created 12.04.2019
 */
public class Image implements CreateAndDestroy {
    private long image;

    private Device device;

    public Image(long image, Device device) {
        this.image = image;
        this.device = device;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        vkDestroyImage(device.get(), image, null);
    }
}
