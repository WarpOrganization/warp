package net.warpgame.engine.graphics.command.queue;

import net.warpgame.engine.graphics.core.Device;
import org.lwjgl.vulkan.VkPresentInfoKHR;

import static org.lwjgl.vulkan.KHRSwapchain.vkQueuePresentKHR;

/**
 * @author MarconZet
 * Created 07.04.2019
 */

public class PresentationQueue extends Queue {

    public PresentationQueue(int familyIndex, int queueIndex, Device device) {
        super(familyIndex, queueIndex, device);
    }

    public synchronized int presentKHR(VkPresentInfoKHR presentInfo){
        return vkQueuePresentKHR(queue, presentInfo);
    }
}
