package net.warpgame.engine.graphics.memory;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public interface Loadable {
    void load(Allocator allocator, CommandPool commandPool);
    void unload(Allocator allocator);
    void schedule(Property property);
    boolean isLoaded();
}
