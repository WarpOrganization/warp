package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Listener<T extends Component, U extends Event> {
    private T owner;
    private Class<U> eventClass;

    public Listener(T owner, Class<U> eventClass) {
        this.owner = owner;
        this.eventClass = eventClass;
        this.owner.addListener(this);
    }

    public T getOwner() {
        return owner;
    }
}
