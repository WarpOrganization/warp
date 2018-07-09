package net.warpgame.engine.core.serialization;

import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.07.2018
 */
@Service
public class Serializers {

    private HashMap<Short, Serialization> serializationMap = new HashMap<>();
    private HashMap<Class, Serialization> classSerializationMap = new HashMap<>();

    public Serializers(Serialization[] serializers) {
        for (Serialization serializationIO : serializers) {
            serializationMap.put((short) serializationIO.getType(), serializationIO);
            classSerializationMap.put(serializationIO.getTargetClass(), serializationIO);
        }
    }

    public Serializers serialize(SerializationBuffer buffer, Object object) {
        Serialization serialization = classSerializationMap.get(object.getClass());
        if (serialization != null) {
            Serializer serializer = new Serializer(this, buffer);
            serializer.write(serialization.getType());
            serialization.serialize(object, serializer);
        } else {
            throw new SerializationNotFoundException(object.getClass().getName());
        }
        return this;
    }

    public Serializers serialize(SerializationBuffer buffer, Object object, int objectTypeId) {
        Serialization serialization = serializationMap.get(objectTypeId);
        if (serialization != null) {
            Serializer serializer = new Serializer(this, buffer);
            serializer.write(objectTypeId);
            serialization.serialize(object, serializer);
        } else {
            throw new SerializationNotFoundException(objectTypeId);
        }
        return this;
    }

    void continueSerialization(Serializer serializer, Object object, int objectTypeId) {
        Serialization serialization = serializationMap.get(objectTypeId);
        if (serialization != null) {
            serializer.write(objectTypeId);
            serialization.serialize(object, serializer);
        }
    }

//    public Object deserialize(SerializationBuffer buffer, Class targetClass) {
//        Serialization serializationIO = classSerializationMap.get(targetClass.getClass());
//        if (serializationIO != null) {
//            return serializationIO.deserialize(buffer, this);
//        } else {
//            throw new SerializationNotFoundException(targetClass.getName());
//        }
//    }

    public Object deserialize(SerializationBuffer buffer) {
        short objectTypeId = buffer.readShort();
        Serialization serialization = serializationMap.get(objectTypeId);
        if (serialization != null) {
            Deserializer deserializer = new Deserializer(this, buffer);
            return serialization.deserialize(deserializer);
        } else {
            throw new SerializationNotFoundException(objectTypeId);
        }
    }

    Object continueDeserialization(Deserializer deserializer) {
        short objectTypeId = deserializer.getShort();
        Serialization serialization = serializationMap.get(objectTypeId);
        if (serialization != null) {
            return serialization.deserialize(deserializer);
        } else {
            throw new SerializationNotFoundException(objectTypeId);
        }
    }

}
