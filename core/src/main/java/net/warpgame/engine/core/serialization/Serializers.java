package net.warpgame.engine.core.serialization;

import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.07.2018
 */
@Service
public class Serializers {

    private HashMap<Integer, Serialization> serializationMap = new HashMap<>();
    private HashMap<Class, Serialization> classSerializationMap = new HashMap<>();

    public Serializers(Serialization[] serializers) {
        for (Serialization serializationIO : serializers) {
            serializationMap.put(serializationIO.getType(), serializationIO);
            classSerializationMap.put(serializationIO.getTargetClass(), serializationIO);
        }
    }

    public void serialize(SerializationBuffer buffer, Object object) {
        Serialization serializationIO = classSerializationMap.get(object.getClass());
        if (serializationIO != null) {
            serializationIO.serialize(object, buffer, this);
        } else {
            throw new SerializationNotFoundException(object.getClass().getName());
        }
    }

    public void serialize(SerializationBuffer buffer, Object object, int objectTypeId) {
        Serialization serializationIO = serializationMap.get(objectTypeId);
        if (serializationIO != null) {
            serializationIO.serialize(object, buffer, this);
        } else {
            throw new SerializationNotFoundException(objectTypeId);
        }
    }

    public Object deserialize(SerializationBuffer buffer, Class targetClass) {
        Serialization serializationIO = classSerializationMap.get(targetClass.getClass());
        if (serializationIO != null) {
            return serializationIO.deserialize(buffer, this);
        } else {
            throw new SerializationNotFoundException(targetClass.getName());
        }
    }

    public Object deserialize(SerializationBuffer buffer, int objectTypeId) {
        Serialization serializationIO = serializationMap.get(objectTypeId);
        if (serializationIO != null) {
            return serializationIO.deserialize(buffer, this);
        } else {
            throw new SerializationNotFoundException(objectTypeId);
        }
    }


}
