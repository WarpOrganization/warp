package net.warpgame.engine.core.property;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 17
 */
public class PropertyEnabledEvent extends Event {
    public static final String PROPERTY_ENABLED_EVENT_NAME = "PropertyEnabledEvent";
    private Property property;

    public PropertyEnabledEvent(Property property) {
        super(PROPERTY_ENABLED_EVENT_NAME);
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
}
