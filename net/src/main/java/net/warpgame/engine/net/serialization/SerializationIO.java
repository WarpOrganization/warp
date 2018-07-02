package net.warpgame.engine.net.serialization;

/**
 * @author Hubertus
 * Created 01.07.2018
 */
public abstract class SerializationIO<T> {
    private Class<T> targetClass;

    public SerializationIO(Class<T> targetClass){
        this.targetClass = targetClass;
    }

    public abstract void serialize(T object, SerializationBuffer buffer, Serializers serializers);
    public abstract T deserialize(SerializationBuffer buffer, Serializers serializers);

    public Class<T> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns generated ID of object this SerializationIO operates on.
     * Method is generated at runtime.
     */
    public int getType() {
        String msg = String.format("Engine runtime was unable to generate the getTypeId method for %s class", getClass().getName());
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Returns generated ID object this SerializationIO operates on.
     * Method is generated and inlined at runtime.
     */
    public static int getTypeId(Class<?> propertyClass){
        String msg = String.format("Engine runtime was unable to inline type ID for %s class", propertyClass.getName());
        throw new UnsupportedOperationException(msg);
    }
}
