package net.warpgame.engine.core.component;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-07-14 at 17
 */
public class ComponentDeathEvent extends Event {

    private Component component;

    public ComponentDeathEvent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
