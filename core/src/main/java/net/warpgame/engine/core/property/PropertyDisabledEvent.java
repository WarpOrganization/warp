package net.warpgame.engine.core.property;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 17
 */
public class PropertyDisabledEvent<T extends Component> extends Event {
    private Property property;

    public PropertyDisabledEvent(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
}