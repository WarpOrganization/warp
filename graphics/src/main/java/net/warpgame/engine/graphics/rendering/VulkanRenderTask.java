package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.CameraProperty;
import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.PresentationQueue;
import net.warpgame.engine.graphics.command.Semaphore;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.joml.Matrix4fc;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkSubmitInfo;

import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static net.warpgame.engine.graphics.GraphicsConfig.MAX_FRAMES_IN_FLIGHT;
import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 30.10.2019
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class VulkanRenderTask extends EngineTask {

    private int currentFrame = 0;
    private Semaphore[] imageAvailableSemaphore;
    private Semaphore[] renderFinishedSemaphore;
    private Fence[] inFlightFences;

    private RecordingTask recordingTask;
    private CameraHolder cameraHolder;

    private GraphicsQueue graphicsQueue;
    private PresentationQueue presentationQueue;
    private SwapChain swapChain;
    private Device device;

    public VulkanRenderTask(RecordingTask recordingTask, CameraHolder cameraHolder, GraphicsQueue graphicsQueue, PresentationQueue presentationQueue, SwapChain swapChain, Device device) {
        this.recordingTask = recordingTask;
        this.cameraHolder = cameraHolder;
        this.graphicsQueue = graphicsQueue;
        this.presentationQueue = presentationQueue;
        this.swapChain = swapChain;
        this.device = device;
    }

    @Override
    protected void onInit() {
        imageAvailableSemaphore = new Semaphore[MAX_FRAMES_IN_FLIGHT];
        renderFinishedSemaphore = new Semaphore[MAX_FRAMES_IN_FLIGHT];
        inFlightFences = new Fence[MAX_FRAMES_IN_FLIGHT];

        for (int i = 0; i < MAX_FRAMES_IN_FLIGHT; i++) {
            imageAvailableSemaphore[i] = new Semaphore(device);
            renderFinishedSemaphore[i] = new Semaphore(device);
            inFlightFences[i] = new Fence(device, VK_FENCE_CREATE_SIGNALED_BIT);
        }
    }

    @Override
    public void update(int delta) {
        if(cameraHolder.getCameraProperty() == null) return;
        drawFrame();
        currentFrame = (currentFrame + 1) % MAX_FRAMES_IN_FLIGHT;
    }

    @Override
    protected void onClose() {
        for (int i = 0; i < MAX_FRAMES_IN_FLIGHT; i++) {
            imageAvailableSemaphore[i].destroy();
            renderFinishedSemaphore[i].destroy();
            inFlightFences[i].destroy();
        }
    }

    private void drawFrame() {
        inFlightFences[currentFrame].block().reset();

        IntBuffer pointer = BufferUtils.createIntBuffer(1);
        int err = vkAcquireNextImageKHR(device.get(), swapChain.get(), Long.MAX_VALUE, imageAvailableSemaphore[currentFrame].get(), VK_NULL_HANDLE, pointer);
        if(err != VK_SUCCESS && err != VK_SUBOPTIMAL_KHR){
            throw new VulkanAssertionError("Failed to acquire next image from KHR", err);
        }
        int imageIndex = pointer.get(0);

        CameraProperty camera = cameraHolder.getCameraProperty();
        camera.update();
        Matrix4fc viewMatrix = camera.getCameraMatrix();
        Matrix4fc projectionMatrix = camera.getProjectionMatrix();

        recordingTask.getVulkanRenders().forEach(x -> x.updateUniformBuffer(viewMatrix, projectionMatrix, imageIndex));

        LongBuffer waitSemaphores = BufferUtils.createLongBuffer(1).put(0, imageAvailableSemaphore[currentFrame].get());
        LongBuffer signalSemaphores = BufferUtils.createLongBuffer(1).put(0, renderFinishedSemaphore[currentFrame].get());
        IntBuffer waitStages = BufferUtils.createIntBuffer(1).put(0, VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
        VkSubmitInfo submitInfo = VkSubmitInfo.create()
                .sType(VK_STRUCTURE_TYPE_SUBMIT_INFO)
                .waitSemaphoreCount(1)
                .pWaitSemaphores(waitSemaphores)
                .pWaitDstStageMask(waitStages)
                .pCommandBuffers(BufferUtils.createPointerBuffer(1).put(0, recordingTask.getCommandBuffers()[imageIndex]))
                .pSignalSemaphores(signalSemaphores);

        err = vkQueueSubmit(graphicsQueue.get(), submitInfo, inFlightFences[currentFrame].get());
        if (err != VK_SUCCESS) {
            throw new VulkanAssertionError("Failed to submit draw command buffer", err);
        }

        VkPresentInfoKHR presentInfo = VkPresentInfoKHR.create()
                .sType(VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
                .pWaitSemaphores(signalSemaphores)
                .swapchainCount(1)
                .pSwapchains(BufferUtils.createLongBuffer(1).put(0, swapChain.get()))
                .pImageIndices(pointer);

        err = vkQueuePresentKHR(presentationQueue.get(), presentInfo);
        if (err != VK_SUCCESS && err != VK_SUBOPTIMAL_KHR) {
            throw new VulkanAssertionError("Failed to present swap chain image", err);
        }
    }

    @Override
    public int getPriority() {
        return 20;
    }
}
