package net.warpgame.engine.graphics;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.graphics.core.InstanceManager;
import net.warpgame.engine.graphics.rendering.pipeline.GraphicsPipeline;
import net.warpgame.engine.graphics.rendering.pipeline.RenderPass;
import net.warpgame.engine.graphics.window.SwapChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.vulkan.VK10.vkDeviceWaitIdle;

/**
 * @author MarconZet
 * Created 05.04.2019
 */

@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class VulkanTask extends EngineTask {
    private static final Logger logger = LoggerFactory.getLogger(VulkanTask.class);

    private InstanceManager instanceManager;
    private SwapChain swapChain;
    private RenderPass renderPass;
    private GraphicsPipeline graphicsPipeline;

    public VulkanTask(InstanceManager instanceManager, SwapChain swapChain, RenderPass renderPass, GraphicsPipeline graphicsPipeline) {
        this.instanceManager = instanceManager;
        this.swapChain = swapChain;
        this.renderPass = renderPass;
        this.graphicsPipeline = graphicsPipeline;
    }

    @Override
    protected void onInit() {
        instanceManager.create();
        logger.info("Creating Vulkan static resources");
        swapChain.create();
        renderPass.create();
        graphicsPipeline.create();
    }

    @Override
    public void update(int delta) {
        glfwPollEvents();
    }

    @Override
    protected void onClose() {
        vkDeviceWaitIdle(instanceManager.getDevice().get());
        logger.info("Destroying Vulkan instance and static resources");
        graphicsPipeline.destroy();
        renderPass.destroy();
        swapChain.destroy();
        instanceManager.destroy();
        logger.info("Terminated Vulkan");
        throw new RuntimeException();//better terminate thread with exception, then with core dump
    }

    @Override
    public int getPriority() {
        return -20;
    }
}
