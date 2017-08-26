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
}
