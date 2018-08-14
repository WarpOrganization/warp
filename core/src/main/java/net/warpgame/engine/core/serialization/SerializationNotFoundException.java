package net.warpgame.engine.core.serialization;

/**
 * @author Hubertus
 * Created 02.07.2018
 */
public class SerializationNotFoundException extends RuntimeException {
    public SerializationNotFoundException(String className) {
        super("Serialization service for class " + className + " not found.");
    }

    public SerializationNotFoundException(int objectTypeId) {
        super("Serialization service for object type " + objectTypeId + " not found");
    }
}
