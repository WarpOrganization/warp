package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.command.Semaphore;
import net.warpgame.engine.graphics.core.Device;

import static net.warpgame.engine.graphics.GraphicsConfig.MAX_FRAMES_IN_FLIGHT;
import static org.lwjgl.vulkan.VK10.VK_FENCE_CREATE_SIGNALED_BIT;

/**
 * @author MarconZet
 * Created 30.10.2019
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class VulkanRenderTask extends EngineTask {

    private Semaphore[] imageAvailableSemaphore;
    private Semaphore[] renderFinishedSemaphore;
    private Fence[] inFlightFences;

    private Device device;

    public VulkanRenderTask(Device device) {
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

    }

    @Override
    protected void onClose() {
        for (int i = 0; i < MAX_FRAMES_IN_FLIGHT; i++) {
            imageAvailableSemaphore[i].destroy();
            renderFinishedSemaphore[i].destroy();
            inFlightFences[i].destroy();
        }
    }

}
