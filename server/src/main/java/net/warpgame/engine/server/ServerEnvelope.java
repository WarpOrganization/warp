package net.warpgame.engine.server;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.net.event.Envelope;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ServerEnvelope extends Envelope {

    private Client targetClient;

    public ServerEnvelope(Event content) {
        super(content);
    }

    public ServerEnvelope(Event content, Client targetClient) {
        super(content);
        this.targetClient = targetClient;
    }

    public Client getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Client targetClient) {
        this.targetClient = targetClient;
    }
}
