package net.warpgame.engine.net.serialization;

/**
 * @author Hubertus
 * Created 02.07.2018
 */
public class SerializationIONotFoundException extends RuntimeException {
    public SerializationIONotFoundException(String className) {
        super("SerializationIO for class " + className + " not found.");
    }

    public SerializationIONotFoundException(int objectTypeId) {
        super("SerializationIO for object type " + objectTypeId + " not found");
    }
}
