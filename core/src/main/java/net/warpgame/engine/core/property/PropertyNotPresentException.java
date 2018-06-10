package net.warpgame.engine.core.property;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class PropertyNotPresentException extends RuntimeException {
    public PropertyNotPresentException(int propertyId) {
        super("Property with id " + propertyId + " is not present in the component.");
    }
    public <T extends Property> PropertyNotPresentException(Class<T> propertyType) {
        super("Property of type " + propertyType.getName() + " is not present in the component.");
    }
}
