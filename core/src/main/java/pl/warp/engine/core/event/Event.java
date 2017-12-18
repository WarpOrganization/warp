package pl.warp.engine.core.event;

import pl.warp.engine.core.property.Property;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Event {
    private String typeName;

    public Event(String typeName) {
        this.typeName = typeName;
    }

    public Event() {
        this.typeName = getClass().getName();
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * Returns generated ID of instance's event type.
     * Method is generated at runtime.
     */
    public int getType() {
        throw new NotImplementedException();
    }

    /**
     * Returns generated ID of event type.
     * Method is generated and inlined at runtime.
     */
    public static int getTypeId(Class<? extends Property> propertyClass){
        throw new NotImplementedException();
    }
}
