package net.warpgame.engine.graphics.command;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.vulkan.VkQueue;

import static org.lwjgl.vulkan.VK10.vkGetDeviceQueue;

/**
 * @author MarconZet
 * Created 07.04.2019
 */
public abstract class Queue implements CreateAndDestroy {
    private VkQueue queue;

    private Device device;

    protected Queue(Device device) {
        this.device = device;
    }

    @Override
    public void create() {
        PointerBuffer pQueue = BufferUtils.createPointerBuffer(1);
        vkGetDeviceQueue(device.get(), getFamily(), 0, pQueue);
        queue = new VkQueue(pQueue.get(0), device.get());
    }

    @Override
    public void destroy() {

    }

    protected abstract int getFamily();

    public VkQueue get() {
        return queue;
    }
}
