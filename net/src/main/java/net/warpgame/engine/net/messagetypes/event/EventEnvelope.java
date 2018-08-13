package net.warpgame.engine.net.messagetypes.event;

import net.warpgame.engine.core.component.Component;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
public class EventEnvelope {
    private NetworkEvent event;
    private Component targetComponent;

    public EventEnvelope(NetworkEvent event, Component targetComponent) {
        this.event = event;
        this.targetComponent = targetComponent;
    }

    public NetworkEvent getEvent() {
        return event;
    }

    public void setEvent(NetworkEvent event) {
        this.event = event;
    }

    public Component getTargetComponent() {
        return targetComponent;
    }

    public void setTargetComponent(Component targetComponent) {
        this.targetComponent = targetComponent;
    }
}
