package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Listener<T extends Component, U extends Event> {
    private T owner;
    private int eventTypeId; //reflection is just too slow

    public Listener(T owner, int eventTypeId) {
        this.owner = owner;
        this.eventTypeId = eventTypeId;
        this.owner.addListener(this);
    }

    public boolean isInterestedIn(Event event) {
        return event.getTypeID() == eventTypeId;
    }

    public abstract void handle(U event);

    public T getOwner() {
        return owner;
    }
}
