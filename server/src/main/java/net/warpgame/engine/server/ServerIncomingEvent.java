package net.warpgame.engine.server;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ServerIncomingEvent extends Event {
    private Client sourceClient;
    private Event incomingEvent;

    public ServerIncomingEvent(Client sourceClient, Event incomingEvent) {
        super("serverIncomingEvent");
        this.sourceClient = sourceClient;
        this.incomingEvent = incomingEvent;
    }

    public Client getSourceClient() {
        return sourceClient;
    }

    public void setSourceClient(Client sourceClient) {
        this.sourceClient = sourceClient;
    }

    public Event getIncomingEvent() {
        return incomingEvent;
    }

    public void setIncomingEvent(Event incomingEvent) {
        this.incomingEvent = incomingEvent;
    }
}
