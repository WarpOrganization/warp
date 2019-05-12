package net.warpgame.engine.graphics.mesh;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.Fence;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Buffer;
import net.warpgame.engine.graphics.memory.Loadable;
import net.warpgame.engine.graphics.memory.VulkanLoadTask;
import net.warpgame.engine.graphics.resource.mesh.Model;
import net.warpgame.engine.graphics.resource.mesh.ObjLoader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_CPU_ONLY;
import static org.lwjgl.util.vma.Vma.VMA_MEMORY_USAGE_GPU_ONLY;
import static org.lwjgl.vulkan.VK10.*;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class StaticMesh implements Loadable {
    private static final Logger logger = LoggerFactory.getLogger(StaticMesh.class);

    private Buffer vertex;
    private Buffer indices;

    private int loaded = 0;
    private final File source;

    private Allocator allocator;

    public StaticMesh(File source) {
        if(!source.isFile()){
            throw new AssertionError(String.format("%s is not a file", source.toString()));
        }
        if(!FilenameUtils.getExtension(source.getName()).equals("obj")){
            throw new AssertionError(String.format("%s is not an obj file", source.toString()));
        }
        this.source = source;
    }

    @Override
    public synchronized void schedule(Property property) {
        if (loaded == 0) {
            loaded = -1;
            property.getOwner().getContext().getLoadedContext().findOne(VulkanLoadTask.class).get().addToLoad(this);
        }
    }

    @Override
    public void load(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException {
        this.allocator = allocator;
        ObjLoader obj = ObjLoader.read(new FileInputStream(source), true);
        Model model = obj.toModel();
        vertex = loadBuffer(model.getVertices(), VK_BUFFER_USAGE_TRANSFER_DST_BIT | VK_BUFFER_USAGE_VERTEX_BUFFER_BIT, commandPool);
        indices = loadBuffer(model.getIndices(), VK_BUFFER_USAGE_TRANSFER_DST_BIT | VK_BUFFER_USAGE_INDEX_BUFFER_BIT, commandPool);
        logger.info("Model loaded");
        loaded = 1;
    }

    private Buffer loadBuffer(ByteBuffer src, int flags, CommandPool commandPool) {
        int bufferSize = src.remaining();
        Buffer stagingBuffer = new Buffer(
                bufferSize,
                VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
                VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK_MEMORY_PROPERTY_HOST_COHERENT_BIT,
                VMA_MEMORY_USAGE_CPU_ONLY,
                allocator
        );
        Buffer.copyBuffer(src, stagingBuffer, bufferSize);
        Buffer res = new Buffer(
                bufferSize,
                flags,
                0,
                VMA_MEMORY_USAGE_GPU_ONLY,
                allocator
        );
        Fence fence = Buffer.copyBuffer(stagingBuffer, res, bufferSize, commandPool);
        fence.block().destroy();
        stagingBuffer.destroy();
        return res;
    }

    @Override
    public void unload() {
        vertex.destroy();
        indices.destroy();
        logger.info("Model unloaded");
        loaded = 0;
    }

    @Override
    public boolean isLoaded() {
        return loaded == 1;
    }
}
