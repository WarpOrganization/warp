package pl.warp.engine.core.event;

import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Listener<T extends Component, U extends Event> {

    private T owner;
    private EventFilterStrategy filterStrategy;

    protected Listener(T owner, EventFilterStrategy filterStrategy) {
        this.owner = owner;
        this.filterStrategy = filterStrategy;
    }

    protected Listener(T owner, String eventTypeName) {
        this(owner, new TypeNameBasedEventFilterStrategy(eventTypeName));
    }

    protected Listener(T owner, Class<U> eventClass) {
        this(owner, new TypeBasedEventFilterStrategy(eventClass));
    }

    public boolean isInterestedIn(Event event) {
        return filterStrategy.apply(event);
    }

    public abstract void handle(U event);

    public void destroy() {
        this.owner.removeListener(this);
    }

    public T getOwner() {
        return owner;
    }

}
