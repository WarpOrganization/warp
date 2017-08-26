package pl.warp.engine.core.property;

import pl.warp.engine.core.event.Event;
import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-12-15 at 17
 */
public class PropertyDisabledEvent<T extends Component> extends Event {
    public static final String PROPERTY_DISABLED_EVENT_NAME = "PropertyEnabledEvent";
    private Property<T> property;

    public PropertyDisabledEvent(Property<T> property) {
        super(PROPERTY_DISABLED_EVENT_NAME);
        this.property = property;
    }

    public Property<T> getProperty() {
        return property;
    }
}