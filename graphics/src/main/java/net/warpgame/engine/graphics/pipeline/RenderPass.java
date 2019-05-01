package net.warpgame.engine.graphics.pipeline;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Image;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.*;

import java.nio.LongBuffer;

import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_GPU_ONLY;
import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 13.04.2019
 */

@Service
public class RenderPass implements CreateAndDestroy {
    private long renderPass;

    private Image depthImage;

    private PhysicalDevice physicalDevice;
    private Device device;
    private Allocator allocator;
    private SwapChain swapChain;

    public RenderPass(PhysicalDevice physicalDevice, Device device, Allocator allocator, SwapChain swapChain) {
        this.physicalDevice = physicalDevice;
        this.device = device;
        this.allocator = allocator;
        this.swapChain = swapChain;
    }

    @Override
    public void create() {
        createRenderPass();
        createDepthImage();
    }

    @Override
    public void destroy() {
        depthImage.destroy();
        vkDestroyRenderPass(device.get(), renderPass, null);
    }

    private void createRenderPass() {
        VkAttachmentDescription.Buffer attachmentDescriptions = VkAttachmentDescription.create(2);

        attachmentDescriptions.get(0)
                .format(swapChain.getImageFormat())
                .samples(VK_SAMPLE_COUNT_1_BIT)
                .loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
                .storeOp(VK_ATTACHMENT_STORE_OP_STORE)
                .stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
                .stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .finalLayout(VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);

        attachmentDescriptions.get(1)
                .format(findDepthFormat())
                .samples(VK_SAMPLE_COUNT_1_BIT)
                .loadOp(VK_ATTACHMENT_LOAD_OP_CLEAR)
                .storeOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                .stencilLoadOp(VK_ATTACHMENT_LOAD_OP_DONT_CARE)
                .stencilStoreOp(VK_ATTACHMENT_STORE_OP_DONT_CARE)
                .initialLayout(VK_IMAGE_LAYOUT_UNDEFINED)
                .finalLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

        VkAttachmentReference.Buffer colorAttachmentRef = VkAttachmentReference.create(1)
                .attachment(0)
                .layout(VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

        VkAttachmentReference depthAttachmentRef = VkAttachmentReference.create()
                .attachment(1)
                .layout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL);

        VkSubpassDescription.Buffer subpass = VkSubpassDescription.create(1)
                .pipelineBindPoint(VK_PIPELINE_BIND_POINT_GRAPHICS)
                .colorAttachmentCount(1)
                .pColorAttachments(colorAttachmentRef)
                .pDepthStencilAttachment(depthAttachmentRef);

        VkSubpassDependency.Buffer dependency = VkSubpassDependency.create(1)
                .srcSubpass(VK_SUBPASS_EXTERNAL)
                .dstSubpass(0)
                .srcStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .srcAccessMask(0)
                .dstStageMask(VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .dstAccessMask(VK_ACCESS_COLOR_ATTACHMENT_READ_BIT | VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

        VkRenderPassCreateInfo renderPassInfo = VkRenderPassCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO)
                .pAttachments(attachmentDescriptions)
                .pSubpasses(subpass)
                .pDependencies(dependency);

        LongBuffer pRenderPass = BufferUtils.createLongBuffer(1);
        int err = vkCreateRenderPass(device.get(), renderPassInfo, null, pRenderPass);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create clear render pass", err);
        }
        this.renderPass = pRenderPass.get(0);
    }

    private void createDepthImage() {
        int depthFormat = findDepthFormat();
        depthImage = new Image(
                swapChain.getExtent().width(),
                swapChain.getExtent().height(),
                depthFormat,
                1,
                VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT,
                VMA_MEMORY_USAGE_GPU_ONLY,
                allocator
        );
        //depthImage = new Image(1024, 1024, VK_FORMAT_R8G8B8A8_UNORM, 1, VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT,  VMA_MEMORY_USAGE_GPU_ONLY, allocator);
        //depthImage.transitionLayout(depthFormat, VK_IMAGE_LAYOUT_UNDEFINED, VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL, 1);
    }

    private int findDepthFormat() {
        return physicalDevice.findSupportedFormat(
                new int[]{VK_FORMAT_D32_SFLOAT, VK_FORMAT_D32_SFLOAT_S8_UINT, VK_FORMAT_D24_UNORM_S8_UINT},
                VK_IMAGE_TILING_OPTIMAL,
                VK_FORMAT_FEATURE_DEPTH_STENCIL_ATTACHMENT_BIT
        );
    }

    public long get() {
        return renderPass;
    }
}
