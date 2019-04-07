package net.warpgame.engine.graphics.core;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.queue.QueueFamilyIndices;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import org.lwjgl.vulkan.VkDevice;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

@Service
public class Device extends CreateAndDestroy {
    private VkDevice device;

    private PhysicalDevice physicalDevice;
    private QueueFamilyIndices indices;

    public Device(PhysicalDevice physicalDevice, QueueFamilyIndices indices) {
        this.physicalDevice = physicalDevice;
        this.indices = indices;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }
}
