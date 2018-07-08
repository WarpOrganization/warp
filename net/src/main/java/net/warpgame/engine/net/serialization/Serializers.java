package net.warpgame.engine.net.serialization;

import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.07.2018
 */
@Service
public class Serializers {

    private HashMap<Integer, SerializationIO> serializationIOMap = new HashMap<>();
    private HashMap<Class, SerializationIO> classSerializationIOMap = new HashMap<>();

    public Serializers(SerializationIO[] serializers) {
        for (SerializationIO serializationIO : serializers) {
//            serializationIOMap.put(serializationIO.getType(), serializationIO);
            classSerializationIOMap.put(serializationIO.getTargetClass(), serializationIO);
        }
    }

    public void serialize(SerializationBuffer buffer, Object object) {
        SerializationIO serializationIO = classSerializationIOMap.get(object.getClass());
        if (serializationIO != null) {
            serializationIO.serialize(object, buffer, this);
        } else {
            throw new SerializationIONotFoundException(object.getClass().getName());
        }
    }

    public void serialize(SerializationBuffer buffer, Object object, int objectTypeId) {
        SerializationIO serializationIO = serializationIOMap.get(objectTypeId);
        if (serializationIO != null) {
            serializationIO.serialize(object, buffer, this);
        } else {
            throw new SerializationIONotFoundException(objectTypeId);
        }
    }

    public Object deserialize(SerializationBuffer buffer, Class targetClass) {
        SerializationIO serializationIO = classSerializationIOMap.get(targetClass.getClass());
        if (serializationIO != null) {
            return serializationIO.deserialize(buffer, this);
        } else {
            throw new SerializationIONotFoundException(targetClass.getName());
        }
    }

    public Object deserialize(SerializationBuffer buffer, int objectTypeId) {
        SerializationIO serializationIO = serializationIOMap.get(objectTypeId);
        if (serializationIO != null) {
            return serializationIO.deserialize(buffer, this);
        } else {
            throw new SerializationIONotFoundException(objectTypeId);
        }
    }


}
