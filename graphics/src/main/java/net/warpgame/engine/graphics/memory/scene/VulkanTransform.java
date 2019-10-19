package net.warpgame.engine.graphics.memory.scene;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Buffer;
import net.warpgame.engine.graphics.memory.Loadable;
import net.warpgame.engine.graphics.memory.VulkanLoadTask;
import net.warpgame.engine.graphics.window.SwapChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_CPU_TO_GPU;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 24.05.2019
 */
public class VulkanTransform implements Loadable {
    private static final Logger logger = LoggerFactory.getLogger(VulkanTransform.class);

    private Buffer[] uniformBuffers;
    private int length;

    private int loaded = 0;

    private Component owner;

    public VulkanTransform() {
    }

    @Override
    public void load(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException {
        long bufferSize = UniformBufferObject.sizeOf();
        length = owner.getContext().getLoadedContext().findOne(SwapChain.class).get().getImages().length;
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
        logger.info("UBO created");
        loaded = 1;
    }

    @Override
    public void unload() {
        for (Buffer uniformBuffer : uniformBuffers) {
            uniformBuffer.destroy();
        }
        logger.info("UBO destroyed");
        loaded = 0;
    }

    @Override
    public void schedule(Property property) {
        if (loaded == 0) {
            loaded = -1;
            owner = property.getOwner();
            owner.getContext().getLoadedContext().findOne(VulkanLoadTask.class).get().addToLoad(this);
        }
    }

    @Override
    public boolean isLoaded() {
        return loaded == 1;
    }
}
