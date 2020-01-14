package net.warpgame.engine.graphics.command.queue;

import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.core.Device;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkSubmitInfo;

import static org.lwjgl.vulkan.VK10.vkGetDeviceQueue;
import static org.lwjgl.vulkan.VK10.vkQueueSubmit;

/**
 * @author MarconZet
 * Created 07.04.2019
 */
public class Queue {
    protected VkQueue queue;

    private int familyIndex;
    private int queueIndex;

    private Device device;

    public Queue(int familyIndex, int queueIndex, Device device) {
        this.familyIndex = familyIndex;
        this.queueIndex = queueIndex;
        this.device = device;
        create();
    }

    public synchronized int submit(VkSubmitInfo submitInfo, Fence fence){
        return vkQueueSubmit(queue, submitInfo, fence.get());
    }

    private void create() {
        PointerBuffer pQueue = BufferUtils.createPointerBuffer(1);
        vkGetDeviceQueue(device.get(), familyIndex, queueIndex, pQueue);
        queue = new VkQueue(pQueue.get(0), device.get());
    }

    public int getFamilyIndex() {
        return familyIndex;
    }

}
