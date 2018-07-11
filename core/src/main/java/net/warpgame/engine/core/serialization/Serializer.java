package net.warpgame.engine.core.serialization;

/**
 * @author Hubertus
 * Created 09.07.2018
 */
public class Serializer {
    private Serializers serializersService;
    private SerializationBuffer buffer;

    public Serializer(Serializers serializersService, SerializationBuffer buffer) {
        this.serializersService = serializersService;
        this.buffer = buffer;
    }

    public Serializer write(Object object, int objectTypeId) {
        serializersService.continueSerialization(this, object, (short)objectTypeId);
        return this;
    }

    public Serializer write(boolean val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(byte val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(char val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(short val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(int val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(long val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(float val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(double val) {
        buffer.write(val);
        return this;
    }

    public Serializer write(String val) {
        buffer.write(val);
        return this;
    }
}
