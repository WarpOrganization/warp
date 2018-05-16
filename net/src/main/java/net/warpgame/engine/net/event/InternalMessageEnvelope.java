package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * Has no target component.
 * @author Hubertus
 * Created 13.05.2018
 */
public class InternalMessageEnvelope extends Envelope{
    public InternalMessageEnvelope(Event content) {
        super(content);
    }

    public InternalMessageEnvelope(Event content, boolean shouldConfirm) {
        super(content, shouldConfirm);
    }

    @Override
    public boolean isInternal() {
        return true;
    }
}
