package net.warpgame.engine.core.event;

import net.warpgame.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Listener<U extends Event> {

    private Component owner;
    private int eventType;
//    private String eventType;

    protected Listener(Component owner, int eventType) {
        this.owner = owner;
//        this.eventType = eventType;
        this.eventType = eventType;
    }
    
//    public String getEventType() {
//        return eventType;
//    }

    public abstract void handle(U event);

    public void destroy() {
        this.owner.removeListener(this);
    }

    public Component getOwner() {
        return owner;
    }

    public int getEventType() {
        return eventType;
    }
}
