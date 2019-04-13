package net.warpgame.engine.graphics.resource.mesh;

import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 13.04.2019
 */
public class Vertex {
    /**
     * Vector3f position
     * Vector2f texture coordinate
     * Vector3f normal
     */
    private float[] data;

    private static int positionOffset(){
        return 0;
    }

    private static int texCordOffset(){
        return 4 * (3);
    }

    private static int normalOffset(){
        return 4 * (3 + 2);
    }

    public static int sizeOf(){
        return 4 * (3 + 2 + 3);
    }

    public static VkVertexInputBindingDescription.Buffer getBindingDescription() {
        VkVertexInputBindingDescription.Buffer bindingDescription = VkVertexInputBindingDescription.create(1);
        bindingDescription.get(0)
                .binding(0)
                .stride(sizeOf())
                .inputRate(VK_VERTEX_INPUT_RATE_VERTEX);
        return bindingDescription;
    }

    public static VkVertexInputAttributeDescription.Buffer getAttributeDescriptions() {
        VkVertexInputAttributeDescription.Buffer attributeDescriptions = VkVertexInputAttributeDescription.create(3);
        attributeDescriptions.get(0)
                .binding(0)
                .location(0)
                .format(VK_FORMAT_R32G32B32_SFLOAT)
                .offset(positionOffset());
        attributeDescriptions.get(1)
                .binding(0)
                .location(1)
                .format(VK_FORMAT_R32G32_SFLOAT)
                .offset(texCordOffset());
        attributeDescriptions.get(2)
                .binding(0)
                .location(2)
                .format(VK_FORMAT_R32G32B32_SFLOAT)
                .offset(normalOffset());
        return attributeDescriptions;
    }

}
