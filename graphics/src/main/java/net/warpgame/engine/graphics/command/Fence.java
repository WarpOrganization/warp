package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkFenceCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 04.05.2019
 */
public class Fence implements Destroyable {
    private long fence;

    private Runnable runnable;

    private Device device;

    public Fence(Device device) {
        this(device, 0);
    }

    public Fence(Device device, int flags){
        this.device = device;
        create(flags);
    }

    public Fence onDestory(Runnable runnable){
        this.runnable = runnable;
        return this;
    }

    @Override
    public void destroy() {
        if(runnable != null)
            runnable.run();
        vkDestroyFence(device.get(), fence, null);
    }

    public boolean isSignaled(){
        int result = vkGetFenceStatus(device.get(), fence);
        if(result == VK_ERROR_DEVICE_LOST){
            throw new VulkanAssertionError("Failed to get fence status", result);
        }
        return result == VK_SUCCESS;
    }

    public Fence block() {
        block(Long.MAX_VALUE);
        return this;
    }

    public boolean block(long timeout){
        int err = vkWaitForFences(device.get(), fence, true, timeout);
        if(err != VK_SUCCESS && err != VK_TIMEOUT){
            throw new VulkanAssertionError("Failed to wait for fences", err);
        }
        return err == VK_SUCCESS;
    }

    private void create(int flags){
        VkFenceCreateInfo fenceInfo = VkFenceCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_FENCE_CREATE_INFO)
                .pNext(VK_NULL_HANDLE)
                .flags(flags);

        LongBuffer pFence = BufferUtils.createLongBuffer(1);
        int err = vkCreateFence(device.get(), fenceInfo, null, pFence);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create fence", err);
        }
        fence = pFence.get();
    }

    public long get(){
        return fence;
    }
}
