package net.warpgame.engine.core.serialization;

/**
 * @author Hubertus
 * Created 01.07.2018
 */
public abstract class Serialization<T> {
    private Class<T> targetClass;

    public Serialization(Class<T> targetClass){
        this.targetClass = targetClass;
    }

    public abstract void serialize(T object, Serializer serializer);
    public abstract Object deserialize(Deserializer deserializer);

    public Class<T> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns generated ID of object this Serialization operates on.
     * Method is generated at runtime.
     */
    public abstract int getType();
    //TODO uncomment when id generation works
    /* {
        String msg = String.format("Engine runtime was unable to generate the getTypeId method for %s class", getClass().getName());
        throw new UnsupportedOperationException(msg);
    }*/

    /**
     * Returns generated ID object this Serialization operates on.
     * Method is generated and inlined at runtime.
     */
    public static int getTypeId(Class<?> clazz){
        String msg = String.format("Engine runtime was unable to inline type ID for %s class", clazz.getName());
        throw new UnsupportedOperationException(msg);
    }
}
