package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 27.12.2017
 */
public class Envelope extends Event {
    private Event content;
    private int targetComponentId;

    public Envelope(Event content) {
        this.content = content;
    }

    public Event getContent() {
        return content;
    }

    public void setContent(Event content) {
        this.content = content;
    }

    public int getTargetComponentId() {
        return targetComponentId;
    }

    public void setTargetComponentId(int targetComponentId) {
        this.targetComponentId = targetComponentId;
    }
}
