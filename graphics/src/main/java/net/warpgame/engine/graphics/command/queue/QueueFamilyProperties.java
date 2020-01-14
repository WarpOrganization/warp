package net.warpgame.engine.graphics.command.queue;

import static org.lwjgl.vulkan.VK10.VK_QUEUE_GRAPHICS_BIT;
import static org.lwjgl.vulkan.VK10.VK_QUEUE_TRANSFER_BIT;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
public class QueueFamilyProperties {
    private int queueFamilyIndex;
    private int queueFlags;
    private int queueCount;
    private boolean presentSupport;

    public QueueFamilyProperties(int queueFamilyIndex, int queueFlags, int queueCount, boolean presentSupport) {
        this.queueFamilyIndex = queueFamilyIndex;
        this.queueFlags = queueFlags;
        this.queueCount = queueCount;
        this.presentSupport = presentSupport;
    }

    public boolean isPresentSupport() {
        return presentSupport;
    }

    public boolean isGraphicsSupport() {
        return (queueFlags & VK_QUEUE_GRAPHICS_BIT) >0;
    }

    public boolean isTransferSupport() {
        return (queueFlags & VK_QUEUE_TRANSFER_BIT) >0;
    }

    public int getQueueFamilyIndex() {
        return queueFamilyIndex;
    }

    public int getQueueCount() {
        return queueCount;
    }
}
