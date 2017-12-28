package pl.warp.engine.core.event;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Event {
    private String typeName;

    public Event(String typeName) {
        this.typeName = typeName;
    }

    public Event() {
        this.typeName = getClass().getName();
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * Returns generated ID of instance's event type.
     * Method is generated at runtime.
     */
    public int getType() {
        String msg = String.format("Engine runtime was unable to generate the getTypeId method for %s class", getClass().getName());
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Returns generated ID of event type.
     * Method is generated and inlined at runtime.
     */
    public static int getTypeId(Class<? extends Event> eventClass){
        String msg = String.format("Engine runtime was unable to inline type ID for %s class", eventClass.getName());
        throw new UnsupportedOperationException(msg);
    }
}
