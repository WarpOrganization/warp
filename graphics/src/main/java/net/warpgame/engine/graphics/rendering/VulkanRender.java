package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Buffer;
import net.warpgame.engine.graphics.memory.scene.DescriptorPool;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;
import net.warpgame.engine.graphics.memory.scene.ubo.UniformBufferObject;
import net.warpgame.engine.graphics.memory.scene.ubo.VulkanTransform;
import net.warpgame.engine.graphics.utility.Destroyable;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkDescriptorBufferInfo;
import org.lwjgl.vulkan.VkDescriptorImageInfo;
import org.lwjgl.vulkan.VkWriteDescriptorSet;

import java.lang.ref.WeakReference;
import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 22.10.2019
 */
public class VulkanRender implements Destroyable {
    private WeakReference<Component> origin;
    private VulkanTransform transform;
    private StaticMesh mesh;
    private Texture texture;
    private long[] descriptorSets;


    public VulkanRender(Component component, VulkanTransform transform, StaticMesh mesh, Texture texture, DescriptorPool descriptorPool, Device device) {
        this.origin = new WeakReference<>(component);
        this.transform = transform;
        this.mesh = mesh;
        this.texture = texture;
        this.descriptorSets = getDescriptorSets(descriptorPool, device);
    }

    public void render(VkCommandBuffer commandBuffer, int frameNumber, long pipelineLayout){
        LongBuffer vertexBuffers = BufferUtils.createLongBuffer(1).put(0, mesh.getVertex().get());
        LongBuffer offsets = BufferUtils.createLongBuffer(1).put(0, 0);
        vkCmdBindVertexBuffers(commandBuffer, 0, vertexBuffers, offsets);

        vkCmdBindIndexBuffer(commandBuffer, mesh.getIndices().get(), 0, VK_INDEX_TYPE_UINT32);

        LongBuffer descriptorSet = BufferUtils.createLongBuffer(1).put(0, descriptorSets[frameNumber]);
        vkCmdBindDescriptorSets(commandBuffer, VK_PIPELINE_BIND_POINT_GRAPHICS, pipelineLayout, 0, descriptorSet, null);

        vkCmdDrawIndexed(commandBuffer, (int)(mesh.getIndices().getSize()), 1, 0, 0, 0);
    }

    private long[] getDescriptorSets(DescriptorPool pool, Device device){
        long[] sets = pool.getDescriptorSets();
        Buffer[] uniformBuffers = transform.getUniformBuffers();
        for (int i = 0; i < uniformBuffers.length; i++) {
            VkDescriptorBufferInfo.Buffer bufferInfo = VkDescriptorBufferInfo.create(1)
                    .buffer(uniformBuffers[i].get())
                    .offset(0)
                    .range(UniformBufferObject.sizeOf());

            VkDescriptorImageInfo.Buffer imageInfo = VkDescriptorImageInfo.create(1)
                    .imageLayout(VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
                    .imageView(texture.getTextureImageView().get())
                    .sampler(texture.getTextureSampler().get());

            VkWriteDescriptorSet.Buffer writeDescriptor = VkWriteDescriptorSet.create(2);
            writeDescriptor.get(0)
                    .sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET)
                    .dstSet(sets[i])
                    .dstBinding(0)
                    .dstArrayElement(0)
                    .descriptorType(VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
                    .pBufferInfo(bufferInfo);

            writeDescriptor.get(1)
                    .sType(VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET)
                    .dstSet(sets[i])
                    .dstBinding(1)
                    .dstArrayElement(0)
                    .descriptorType(VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
                    .pImageInfo(imageInfo);

            vkUpdateDescriptorSets(device.get(), writeDescriptor, null);
        }
        return sets;
    }

    @Override
    public void destroy() {

    }
}
