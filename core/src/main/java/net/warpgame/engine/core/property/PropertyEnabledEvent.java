package net.warpgame.engine.core.property;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 17
 */
public class PropertyEnabledEvent extends Event {
    private Property property;

    public PropertyEnabledEvent(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
}
