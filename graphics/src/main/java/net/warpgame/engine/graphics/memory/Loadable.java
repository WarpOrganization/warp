package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;

import java.io.FileNotFoundException;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public interface Loadable {
    void load(Allocator allocator, CommandPool commandPool) throws FileNotFoundException;
    void unload();
    void schedule(Property property);
    boolean isLoaded();
}
