package net.warpgame.engine.graphics.memory.scene.ubo;

import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Buffer;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import net.warpgame.engine.graphics.window.SwapChain;

import java.io.FileNotFoundException;

import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_CPU_TO_GPU;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 24.05.2019
 */
public class VulkanTransform extends Loadable {
    private Buffer[] uniformBuffers;
    private int length;

    public VulkanTransform() {
    }

    @Override
    public void load(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException {
        long bufferSize = UniformBufferObject.sizeOf();
        length = loadedContext.findOne(SwapChain.class).get().getImages().length;
        uniformBuffers = new Buffer[length];

        for (int i = 0; i < length; i++) {
            uniformBuffers[i] = new Buffer(
                    bufferSize,
                    VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
                    VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
                    VMA_MEMORY_USAGE_CPU_TO_GPU,
                    allocator
            );
        }
    }

    @Override
    public void unload() {
        for (Buffer uniformBuffer : uniformBuffers) {
            uniformBuffer.destroy();
        }
    }
}
