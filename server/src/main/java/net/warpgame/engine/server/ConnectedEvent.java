package net.warpgame.engine.server;

import net.warpgame.engine.core.event.Event;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ConnectedEvent extends Event {

    private Client connectedClient;

    public ConnectedEvent(Client connectedClient) {
        super("connectedEvent");
        this.connectedClient = connectedClient;
    }

    public Client getConnectedClient() {
        return connectedClient;
    }
}
