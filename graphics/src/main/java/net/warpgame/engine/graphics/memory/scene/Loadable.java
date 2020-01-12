package net.warpgame.engine.graphics.memory.scene;

import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.poll.CommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.Allocator;
import net.warpgame.engine.graphics.memory.ResourceLoadTask;

import java.io.FileNotFoundException;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public abstract class Loadable {
    public final static int
            NOT_LOADED = 0,
            SCHEDULED_TO_LOAD = 1,
            LOADED = 2,
            SCHEDULED_TO_UNLOAD = 3,
            UNLOADED = 4;

    private int loadStatus = NOT_LOADED;

    private ResourceLoadTask resourceLoadTask;
    protected Context loadedContext;

    public void loadResource(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException {
        load(device, allocator, commandPool);
        loadStatus = LOADED;
    }

    public void unloadResource(){
        unload();
        loadStatus = UNLOADED;
    }

    protected abstract void load(Device device, Allocator allocator, CommandPool commandPool) throws FileNotFoundException;

    protected abstract void unload();

    public synchronized void scheduleForLoad(Property property) {
        if (loadStatus == NOT_LOADED) {
            loadStatus = SCHEDULED_TO_LOAD;
            loadedContext = property.getOwner().getContext().getLoadedContext();
            resourceLoadTask = loadedContext.findOne(ResourceLoadTask.class).get();
            resourceLoadTask.addToLoad(this);
        }
    }

    public synchronized void scheduleForUnload(Property property) {
        if (loadStatus == LOADED) {
            loadStatus = SCHEDULED_TO_UNLOAD;
            resourceLoadTask.addToUnload(this);
        }
    }

    public int getLoadStatus() {
        return loadStatus;
    }


}
