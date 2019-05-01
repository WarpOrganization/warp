package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;

import static org.lwjgl.vulkan.VK10.VK_COMMAND_POOL_CREATE_TRANSIENT_BIT;

/**
 * @author MarconZet
 * Created 01.05.2019
 */
public class OneTimeCommandPool extends CommandPool {
    public OneTimeCommandPool(Device device, Queue queue) {
        super(device, queue);
    }

    @Override
    protected int getFlags() {
        return VK_COMMAND_POOL_CREATE_TRANSIENT_BIT;
    }
}
