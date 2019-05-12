package net.warpgame.engine.graphics.pipeline;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.OneTimeCommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Framebuffer;
import net.warpgame.engine.graphics.memory.Image;
import net.warpgame.engine.graphics.memory.ImageView;
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
@Profile("graphics")
public class RenderPass implements CreateAndDestroy {
    private long renderPass;

    private Framebuffer[] framebuffers;
    private ImageView depthImageView;
    private Image depthImage;
    private CommandPool commandPool;

    private PhysicalDevice physicalDevice;
    private Device device;
    private GraphicsQueue graphicsQueue;
    private Allocator allocator;
    private SwapChain swapChain;

    public RenderPass(PhysicalDevice physicalDevice, Device device, GraphicsQueue graphicsQueue, Allocator allocator, SwapChain swapChain) {
        this.physicalDevice = physicalDevice;
        this.device = device;
        this.graphicsQueue = graphicsQueue;
        this.allocator = allocator;
        this.swapChain = swapChain;
    }

    @Override
    public void create() {
        commandPool = new OneTimeCommandPool(device, graphicsQueue);
        createRenderPass();
        createDepthImage();
        createFramebuffers();
    }

    @Override
    public void destroy() {
        for (Framebuffer framebuffer : framebuffers) {
            framebuffer.destroy();
        }
        depthImageView.destroy();
        depthImage.destroy();
        vkDestroyRenderPass(device.get(), renderPass, null);
        commandPool.destroy();
    }

    private void createFramebuffers(){
        framebuffers = new Framebuffer[swapChain.getImageViews().length];
        for (int i = 0; i < framebuffers.length; i++) {
            framebuffers[i] = new Framebuffer(swapChain.getImageViews()[i], depthImageView, device, swapChain, this);
        }
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
        depthImageView = new ImageView(depthImage, VK_IMAGE_ASPECT_DEPTH_BIT, device);
        depthImage.transitionLayout(VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL, commandPool).block().destroy();
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
