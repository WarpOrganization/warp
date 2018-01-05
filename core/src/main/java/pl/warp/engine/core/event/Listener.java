package pl.warp.engine.core.event;

import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Listener<U extends Event> {

    private Component owner;
    private String eventName;

    protected Listener(Component owner, String eventName) {
        this.owner = owner;
        this.eventName = eventName;
    }
    
    public String getEventName() {
        return eventName;
    }

    public abstract void handle(U event);

    public void destroy() {
        this.owner.removeListener(this);
    }

    public Component getOwner() {
        return owner;
    }

}
