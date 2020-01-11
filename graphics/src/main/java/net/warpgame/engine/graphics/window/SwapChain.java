package net.warpgame.engine.graphics.window;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.command.queue.QueueManager;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.core.PhysicalDevice;
import net.warpgame.engine.graphics.memory.Image;
import net.warpgame.engine.graphics.memory.ImageView;
import net.warpgame.engine.graphics.utility.CreateAndDestroy;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;

import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static org.lwjgl.vulkan.KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.04.2019
 */

@Service
@Profile("graphics")
public class SwapChain implements CreateAndDestroy {
    private long swapChain = -1;

    private VkExtent2D extent;
    private int imageFormat;
    private Image[] images;
    private ImageView[] imageViews;

    private PhysicalDevice physicalDevice;
    private Device device;
    private Window window;
    private QueueManager queueManager;

    public SwapChain(PhysicalDevice physicalDevice, Device device, Window window, QueueManager queueManager) {
        this.physicalDevice = physicalDevice;
        this.device = device;
        this.window = window;
        this.queueManager = queueManager;
    }

    @Override
    public void create() {
        createSwapChain();
        getSwapChainImages();
        createImageViews();
        created = true;
    }

    @Override
    public void destroy() {
        for (ImageView imageView : imageViews) {
            imageView.destroy();
        }
        vkDestroySwapchainKHR(device.get(), swapChain, null);
    }


    private boolean created = false;
    @Override
    public boolean isCreated() {
        return created;
    }

    private void createSwapChain() {
        SwapChainSupportDetails swapChainSupport = new SwapChainSupportDetails(window, physicalDevice);

        VkSurfaceFormatKHR surfaceFormat = swapChainSupport.chooseSwapSurfaceFormat();
        int presentMode = swapChainSupport.chooseSwapPresentMode();
        extent = swapChainSupport.chooseSwapExtent(window);
        imageFormat = surfaceFormat.format();

        int imageCount = swapChainSupport.getCapabilities().minImageCount() + 1;
        if (swapChainSupport.getCapabilities().maxImageCount() > 0) {
            imageCount = Math.min(imageCount, swapChainSupport.getCapabilities().maxImageCount());
        }

        VkSwapchainCreateInfoKHR createInfo = VkSwapchainCreateInfoKHR.create()
                .sType(VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
                .surface(window.getSurface())
                .minImageCount(imageCount)
                .imageFormat(surfaceFormat.format())
                .imageColorSpace(surfaceFormat.colorSpace())
                .imageExtent(extent)
                .imageArrayLayers(1)
                .imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
                .preTransform(swapChainSupport.getCapabilities().currentTransform())
                .compositeAlpha(VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
                .presentMode(presentMode)
                .clipped(true)
                .oldSwapchain(VK_NULL_HANDLE);

        if (queueManager.isPresentationUnique()) {
            IntBuffer indices = BufferUtils.createIntBuffer(2)
                    .put(queueManager.getGraphicsFamily())
                    .put(queueManager.getPresentFamily());
            indices.flip();
            createInfo.imageSharingMode(VK_SHARING_MODE_CONCURRENT)
                    .pQueueFamilyIndices(indices);
        } else {
            createInfo.imageSharingMode(VK_SHARING_MODE_EXCLUSIVE)
                    .pQueueFamilyIndices(null);
        }

        LongBuffer pSwapChain = BufferUtils.createLongBuffer(1);
        int err = vkCreateSwapchainKHR(device.get(), createInfo, null, pSwapChain);
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to create swap chain", err);
        }
        swapChain = pSwapChain.get(0);
    }

    private void getSwapChainImages() {
        IntBuffer pImageCount = BufferUtils.createIntBuffer(1);
        int err = vkGetSwapchainImagesKHR(device.get(), swapChain, pImageCount, null);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to get number of swap chain images", err);
        }
        int imageCount = pImageCount.get(0);

        LongBuffer swapChainImages = BufferUtils.createLongBuffer(imageCount);
        err = vkGetSwapchainImagesKHR(device.get(), swapChain, pImageCount, swapChainImages);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to get swap chain images", err);
        }
        Image[] res = new Image[imageCount];
        for (int i = 0; i < res.length; i++) {
            res[i] = new Image(swapChainImages.get(i), imageFormat, 1, device);
        }
        this.images = res;
    }

    private void createImageViews() {
        imageViews = new ImageView[images.length];
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(images[i], VK_IMAGE_ASPECT_COLOR_BIT, device);
        }
    }

    public long get() {
        return swapChain;
    }

    public VkExtent2D getExtent() {
        return extent;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public Image[] getImages() {
        return images;
    }

    public int getImageFormat() {
        return imageFormat;
    }
}
