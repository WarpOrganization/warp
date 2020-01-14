package net.warpgame.engine.graphics.memory.scene.material;

import net.warpgame.engine.graphics.command.poll.CommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Buffer;
import net.warpgame.engine.graphics.memory.Image;
import net.warpgame.engine.graphics.memory.ImageView;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_CPU_ONLY;
import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_GPU_ONLY;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 12.05.2019
 */
public class Texture extends Loadable {
    private TextureSampler textureSampler;
    private ImageView textureImageView;
    private Image textureImage;

    private File source;

    public Texture(File source) {
        this.source = source;
    }


    @Override
    public void load(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException {
        IntBuffer[] textureInfo = new IntBuffer[3];
        for (int i = 0; i < textureInfo.length; i++) {
            textureInfo[i] = BufferUtils.createIntBuffer(1);
        }
        String path = source.getAbsolutePath();
        ByteBuffer pixels = stbi_load(path, textureInfo[0], textureInfo[1], textureInfo[2], STBI_rgb_alpha);
        int texWidth = textureInfo[0].get();
        int texHeight = textureInfo[1].get();
        int texChannels = textureInfo[2].get();
        long imageSize = texWidth * texHeight * 4;
        if(pixels == null){
            throw new FileNotFoundException("Failed to load texture image");
        }
        int mipLevels = (int) Math.floor(Math.log(Math.max(texWidth, texHeight)) / Math.log(2));

        Buffer stagingBuffer = new Buffer(
                imageSize,
                VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
                VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
                VMA_MEMORY_USAGE_CPU_ONLY,
                allocator
        );
        Buffer.copyBuffer(pixels, stagingBuffer, imageSize);
        stbi_image_free(pixels);

        textureImage = new Image(
                texWidth,
                texHeight,
                VK_FORMAT_R8G8B8A8_UNORM,
                mipLevels,
                VK_IMAGE_USAGE_TRANSFER_SRC_BIT | VK_IMAGE_USAGE_TRANSFER_DST_BIT | VK_IMAGE_USAGE_SAMPLED_BIT,
                VMA_MEMORY_USAGE_GPU_ONLY,
                allocator
        );
        textureImage.transitionLayout(VK_IMAGE_LAYOUT_TRANSFER_DST_OPTIMAL, commandPool).block().destroy();
        Buffer.copyBuffer(stagingBuffer, textureImage, texWidth, texHeight, commandPool).block().destroy();
        textureImage.generateMipmaps(commandPool).block().destroy();

        stagingBuffer.destroy();
        textureImageView = new ImageView(textureImage, VK_IMAGE_ASPECT_COLOR_BIT, device);
        textureSampler = new TextureSampler(device, mipLevels);
    }

    @Override
    public void unload() {
        textureImageView.destroy();
        textureImage.destroy();
    }

    public TextureSampler getTextureSampler() {
        return textureSampler;
    }

    public ImageView getTextureImageView() {
        return textureImageView;
    }
}
