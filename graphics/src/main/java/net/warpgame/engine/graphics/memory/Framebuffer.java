package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.pipeline.RenderPass;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 01.05.2019
 */
public class Framebuffer implements CreateAndDestroy {
    private long framebuffer;

    private ImageView swapChainImageView;
    private ImageView depthImageView;

    private Device device;
    private SwapChain swapChain;
    private RenderPass renderPass;

    public Framebuffer(ImageView swapChainImageView, ImageView depthImageView, Device device, SwapChain swapChain, RenderPass renderPass) {
        this.swapChainImageView = swapChainImageView;
        this.depthImageView = depthImageView;
        this.device = device;
        this.swapChain = swapChain;
        this.renderPass = renderPass;
    }

    @Override
    public void create() {
        LongBuffer attachments = BufferUtils.createLongBuffer(2)
                .put(swapChainImageView.get())
                .put(depthImageView.get());
        attachments.flip();

        VkFramebufferCreateInfo framebufferInfo = VkFramebufferCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
                .renderPass(renderPass.get())
                .pAttachments(attachments)
                .width(swapChain.getExtent().width())
                .height(swapChain.getExtent().height())
                .layers(1);

        LongBuffer pFramebuffer = BufferUtils.createLongBuffer(1);
        int err = vkCreateFramebuffer(device.get(), framebufferInfo, null, pFramebuffer);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create framebuffer", err);
        }
        framebuffer = pFramebuffer.get(0);
    }

    @Override
    public void destroy() {
        vkDestroyFramebuffer(device.get(), framebuffer, null);
    }
}
