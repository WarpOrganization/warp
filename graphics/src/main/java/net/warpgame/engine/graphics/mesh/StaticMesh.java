package net.warpgame.engine.graphics.mesh;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.Loadable;
import net.warpgame.engine.graphics.memory.VulkanLoadTask;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class StaticMesh implements Loadable {
    private static final Logger logger = LoggerFactory.getLogger(StaticMesh.class);

    private int loaded = 0;
    private final File source;

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
    public void load(Allocator allocator, CommandPool commandPool) {
        logger.info("Model loaded");
        loaded = 1;
    }

    @Override
    public void unload(Allocator allocator) {
        logger.info("Model unloaded");
        loaded = 0;
    }

    @Override
    public boolean isLoaded() {
        return loaded == 1;
    }
}
