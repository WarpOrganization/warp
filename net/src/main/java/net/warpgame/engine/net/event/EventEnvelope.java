package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 14.05.2018
 */
public class EventEnvelope extends Envelope {

    public EventEnvelope(Event content) {
        super(content);
    }

    public EventEnvelope(Event content, boolean shouldConfirm) {
        super(content, shouldConfirm);
    }

    @Override
    public boolean isInternal() {
        return false;
    }
}
