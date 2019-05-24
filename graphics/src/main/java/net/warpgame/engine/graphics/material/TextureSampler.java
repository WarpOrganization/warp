package net.warpgame.engine.graphics.material;

import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.utility.Destroyable;
import net.warpgame.engine.graphics.utility.VulkanAssertionError;
import org.lwjgl.BufferUtils;
import org.lwjgl.vulkan.VkSamplerCreateInfo;

import java.nio.LongBuffer;

import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 24.05.2019
 */
public class TextureSampler implements Destroyable {
    private long textureSampler;

    private Device device;

    public TextureSampler(Device device, int mipLevels) {
        this.device = device;
        createSampler(mipLevels);
    }

    @Override
    public void destroy() {
        vkDestroySampler(device.get(), textureSampler, null);
    }

    private void createSampler(int mipLevels) {
        VkSamplerCreateInfo samplerCreateInfo = VkSamplerCreateInfo.create()
                .sType(VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO)
                .magFilter(VK_FILTER_LINEAR)
                .minFilter(VK_FILTER_LINEAR)
                .addressModeU(VK_SAMPLER_ADDRESS_MODE_REPEAT)
                .addressModeV(VK_SAMPLER_ADDRESS_MODE_REPEAT)
                .addressModeW(VK_SAMPLER_ADDRESS_MODE_REPEAT)
                .anisotropyEnable(true)
                .maxAnisotropy(16)
                .borderColor(VK_BORDER_COLOR_INT_OPAQUE_BLACK)
                .unnormalizedCoordinates(false)
                .compareEnable(false)
                .compareOp(VK_COMPARE_OP_ALWAYS)
                .mipmapMode(VK_SAMPLER_MIPMAP_MODE_LINEAR)
                .minLod(0f)
                .maxLod((float)mipLevels)
                .mipLodBias(0f);

        LongBuffer pointer = BufferUtils.createLongBuffer(1);
        int err = vkCreateSampler(device.get(), samplerCreateInfo, null, pointer);
        if(err != VK_SUCCESS){
            throw new VulkanAssertionError("Failed to create texture sampler", err);
        }
        textureSampler = pointer.get(0);
    }

    public long get(){
        return textureSampler;
    }

}
