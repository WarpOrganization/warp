package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.StandardCommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.scene.DescriptorPool;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import net.warpgame.engine.graphics.memory.scene.material.MaterialProperty;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;
import net.warpgame.engine.graphics.memory.scene.ubo.VulkanTransform;
import net.warpgame.engine.graphics.rendering.pipeline.GraphicsPipeline;
import net.warpgame.engine.graphics.rendering.pipeline.RenderPass;
import net.warpgame.engine.graphics.rendering.ui.CanvasProperty;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import net.warpgame.engine.graphics.window.SwapChain;
import org.lwjgl.vulkan.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 22.10.2019
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class RecordingTask extends EngineTask {
    private static final Logger logger = LoggerFactory.getLogger(RecordingTask.class);

    private VkCommandBuffer[] commandBuffers;
    private VkCommandBuffer[] oldCommandBuffers;
    private boolean commandBuffersOffered;
    private boolean recreate;
    private CommandPool commandPool;
    private Set<Component> registeredComponents = Collections.newSetFromMap(new WeakHashMap<>());
    private Set<VulkanRender> vulkanRenders = new HashSet<>();

    private SceneHolder sceneHolder;

    private DescriptorPool descriptorPool;
    private GraphicsQueue graphicsQueue;
    private RenderPass renderPass;
    private SwapChain swapChain;
    private GraphicsPipeline graphicsPipeline;
    private Device device;

    public RecordingTask(DescriptorPool descriptorPool, GraphicsQueue graphicsQueue, Device device, SceneHolder sceneHolder, RenderPass renderPass, SwapChain swapChain, GraphicsPipeline graphicsPipeline) {
        this.descriptorPool = descriptorPool;
        this.graphicsQueue = graphicsQueue;
        this.device = device;
        this.renderPass = renderPass;
        this.swapChain = swapChain;
        this.graphicsPipeline = graphicsPipeline;
        this.recreate = true;
        this.sceneHolder = sceneHolder;
    }

    @Override
    protected void onInit() {
        descriptorPool.create();
        commandPool = new StandardCommandPool(device, graphicsQueue);
    }

    @Override
    public void update(int delta) {
        if (recreate) {
            recreate = false;
            collectComponents(sceneHolder.getScene());
            VkCommandBuffer[] result = recordCommandBuffers();
            //TODO make render thread stop here
            oldCommandBuffers = commandBuffers;
            commandBuffers = result;
            if(!commandBuffersOffered && oldCommandBuffers != null) {
                commandPool.freeCommandBuffer(oldCommandBuffers);
                oldCommandBuffers = null;
            }
            commandBuffersOffered = false;
            //and here render thread should start
        }
    }

    @Override
    protected void onClose() {
        vulkanRenders.forEach(VulkanRender::destroy);
        commandPool.destroy();
        descriptorPool.destroy();
    }

    private VkCommandBuffer[] recordCommandBuffers(){
        VkCommandBuffer[] commandBuffers = commandPool.createCommandBuffer(swapChain.getImages().length);
        for (int i = 0; i < commandBuffers.length; i++) {
            VkCommandBufferBeginInfo bufferBeginInfo = VkCommandBufferBeginInfo.create()
                    .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
                    .flags(VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT)
                    .pInheritanceInfo(null);

            int err = vkBeginCommandBuffer(commandBuffers[i], bufferBeginInfo);
            if (err != VK_SUCCESS) {
                throw new VulkanAssertionError("Failed to begin recording command buffer", err);
            }

            VkClearValue.Buffer clearValues = VkClearValue.create(2);
            clearValues.get(0).color()
                    .float32(0, 100 / 255.0f)
                    .float32(1, 149 / 255.0f)
                    .float32(2, 237 / 255.0f)
                    .float32(3, 1.0f);

            clearValues.get(1).depthStencil().set(1.0f, 0);

            VkRenderPassBeginInfo renderPassBeginInfo = VkRenderPassBeginInfo.create()
                    .sType(VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO)
                    .renderPass(renderPass.get())
                    .framebuffer(renderPass.getFramebuffers()[i].get())
                    .pClearValues(clearValues);
            renderPassBeginInfo.renderArea()
                    .offset(VkOffset2D.create().set(0, 0))
                    .extent(swapChain.getExtent());

            vkCmdBeginRenderPass(commandBuffers[i], renderPassBeginInfo, VK_SUBPASS_CONTENTS_INLINE);

            vkCmdBindPipeline(commandBuffers[i], VK_PIPELINE_BIND_POINT_GRAPHICS, graphicsPipeline.get());

            final int fi = i;
            vulkanRenders.forEach(x -> x.render(commandBuffers[fi], fi, graphicsPipeline.getPipelineLayout()));

            vkCmdEndRenderPass(commandBuffers[i]);

            err = vkEndCommandBuffer(commandBuffers[i]);
            if (err != VK_SUCCESS) {
                throw new VulkanAssertionError("Failed to record command buffer", err);
            }
        }

        return commandBuffers;
    }

    private void collectComponents(Component component) {
        if (component.hasProperty(Property.getTypeId(CanvasProperty.class))) return;
        component.forEachChildren(this::collectComponents);
        if(registeredComponents.contains(component)) return;
        MaterialProperty materialProperty = component.getPropertyOrNull(Property.getTypeId(MaterialProperty.class));
        MeshProperty meshProperty = component.getPropertyOrNull(Property.getTypeId(MeshProperty.class));
        if(materialProperty != null && meshProperty != null){
            Texture texture = materialProperty.getTexture();
            StaticMesh mesh = meshProperty.getMesh();
            VulkanTransform transform = meshProperty.getVulkanTransform();
            if(Stream.of(texture, mesh, transform).allMatch(x -> x.getLoadStatus() == Loadable.LOADED)){
                registeredComponents.add(component);
                VulkanRender render = new VulkanRender(component, transform, mesh, texture, descriptorPool, device);
                vulkanRenders.add(render);
            }
        }
    }

    public Set<VulkanRender> getVulkanRenders() {
        return vulkanRenders;
    }

    public VkCommandBuffer[] getCommandBuffers() {
        commandBuffersOffered = true;
        if(oldCommandBuffers != null){
            commandPool.freeCommandBuffer(oldCommandBuffers);
            oldCommandBuffers = null;
        }
        return commandBuffers;
    }

    public void setRecreate(boolean recreate) {
        this.recreate = recreate;
    }
}
