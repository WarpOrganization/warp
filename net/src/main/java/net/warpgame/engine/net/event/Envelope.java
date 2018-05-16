package net.warpgame.engine.net.event;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 27.12.2017
 */
public abstract class Envelope extends Event {
    private Event content;
    private Component targetComponent;
    private boolean shouldConfirm = false;

    public Envelope(Event content) {
        this.content = content;
    }

    public Envelope(Event content, boolean shouldConfirm) {
        this.content = content;
        this.shouldConfirm = shouldConfirm;
    }

    public Event getContent() {
        return content;
    }

    public void setContent(Event content) {
        this.content = content;
    }

    public Component getTargetComponent() {
        return targetComponent;
    }

    public void setTargetComponent(Component targetComponent) {
        this.targetComponent = targetComponent;
    }

    public boolean isShouldConfirm() {
        return shouldConfirm;
    }

    public void setShouldConfirm(boolean shouldConfirm) {
        this.shouldConfirm = shouldConfirm;
    }

    public abstract boolean isInternal();
}
