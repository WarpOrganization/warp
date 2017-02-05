package pl.warp.engine.core.scene.observable;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;
import pl.warp.engine.core.scene.Property;

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
