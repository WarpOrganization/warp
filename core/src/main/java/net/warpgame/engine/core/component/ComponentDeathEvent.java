package net.warpgame.engine.core.component;

import net.warpgame.engine.core.event.Event;

/**
 * @author Jaca777
 *         Created 2016-07-14 at 17
 */
public class ComponentDeathEvent extends Event {
    public static final String COMPONENT_DEATH_EVENT_NAME = "componentDeath";

    private Component component;

    public ComponentDeathEvent(Component component) {
        super(COMPONENT_DEATH_EVENT_NAME);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
