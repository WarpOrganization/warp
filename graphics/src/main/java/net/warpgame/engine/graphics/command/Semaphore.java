package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 30.10.2019
 */
public class Semaphore implements Destroyable {
    private long semaphore;

    private Device device;

    public Semaphore(Device device) {
        this.device = device;
        create();
    }

    @Override
    public void destroy() {
        vkDestroySemaphore(device.get(), semaphore, null);
    }

    private void create(){
        VkSemaphoreCreateInfo semaphoreCreateInfo = VkSemaphoreCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);
        LongBuffer pointer = BufferUtils.createLongBuffer(1);
        int err = vkCreateSemaphore(device.get(), semaphoreCreateInfo, null, pointer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create semaphore", err);
        }
        this.semaphore = pointer.get();
    }

    public long get(){
        return this.semaphore;
    }
}
