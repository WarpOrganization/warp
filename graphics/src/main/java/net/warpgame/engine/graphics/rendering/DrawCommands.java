package net.warpgame.engine.graphics.rendering;

import org.lwjgl.vulkan.VkCommandBuffer;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
public class DrawCommands {
    private VkCommandBuffer[] commandBuffers;
    private long lastFrame = -1;

    public DrawCommands(VkCommandBuffer[] commandBuffers) {
        this.commandBuffers = commandBuffers;
    }

    public VkCommandBuffer[] getCommandBuffers() {
        return commandBuffers;
    }

    public long getLastFrame() {
        return lastFrame;
    }

    public void setLastFrame(long lastFrame) {
        this.lastFrame = lastFrame;
    }
}
