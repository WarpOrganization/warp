package net.warpgame.engine.net.event;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 08.01.2018
 */
public class EventConfirmationEvent extends Event{
    private Event confirmedEvent;

    public EventConfirmationEvent(Event confirmedEvent) {
        this.confirmedEvent = confirmedEvent;
    }

    public Event getConfirmedEvent() {
        return confirmedEvent;
    }

    public void setConfirmedEvent(Event confirmedEvent) {
        this.confirmedEvent = confirmedEvent;
    }
}
