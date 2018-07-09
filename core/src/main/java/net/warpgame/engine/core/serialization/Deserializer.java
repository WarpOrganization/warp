package net.warpgame.engine.core.serialization;

/**
 * @author Hubertus
 * Created 09.07.2018
 */
public class Deserializer {

    private Serializers serializersService;
    private SerializationBuffer buffer;

    public Deserializer(Serializers serializersService, SerializationBuffer buffer){
        this.serializersService = serializersService;
        this.buffer = buffer;
    }

    public Object getObject(){
        return serializersService.continueDeserialization(this);
    }

    public boolean getBoolean(){
        return buffer.readBoolean();
    }

    public short getShort(){
        return buffer.readShort();
    }

    public char getChar(){
        return buffer.readChar();
    }

    public int getInt(){
        return buffer.readInt();
    }

    public long getLong(){
        return buffer.readLong();
    }

    public float getFloat(){
        return buffer.readFloat();
    }

    public double getDouble(){
        return buffer.readDouble();
    }

    public String getString(){
        return buffer.readString();
    }
}
