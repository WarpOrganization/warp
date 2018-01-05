package net.warpgame.engine.core.property.observable;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.property.Property;

/**
 * @author Hubertus
 *         Created 04.02.17
 */
public class PropertyAddedEvent<T extends Property, S extends Component> extends Event {

    public static final String PROPERTY_CREATED_EVENT_NAME = "propertyCreated";

    private T property;
    private S owner;

    public PropertyAddedEvent(T property, S owner){
        super(PROPERTY_CREATED_EVENT_NAME);
        this.property = property;
        this.owner = owner;
    }

    public T getProperty() {
        return property;
    }

    public S getOwner() {
        return owner;
    }
}
