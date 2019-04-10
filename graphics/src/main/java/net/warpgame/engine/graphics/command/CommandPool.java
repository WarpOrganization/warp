package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
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

    public CommandPool(Device device) {
        this.device = device;
    }

    @Override
    public void create() {
        VkCommandPoolCreateInfo createInfo = VkCommandPoolCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                .queueFamilyIndex(getFamily())
                .flags(0);


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

    protected abstract int getFamily();

    public long get(){
        return commandPool;
    }
}
