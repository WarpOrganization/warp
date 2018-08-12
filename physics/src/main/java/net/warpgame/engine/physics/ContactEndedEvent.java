package net.warpgame.engine.physics;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 12.08.2018
 */
public class ContactEndedEvent extends Event {
    private Component otherComponent;

    public ContactEndedEvent(Component otherComponent) {
        this.otherComponent = otherComponent;
    }

    public Component getOtherComponent() {
        return otherComponent;
    }

    public void setOtherComponent(Component otherComponent) {
        this.otherComponent = otherComponent;
    }
}
