package net.warpgame.engine.server.envelope;

import net.warpgame.engine.net.event.AddressedEnvelope;
import net.warpgame.engine.server.Client;

/**
 * @author Hubertus
 * Created 15.05.2018
 */
public abstract class ServerAddresedEnvelope extends AddressedEnvelope {
    private Client targetClient;

    public Client getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Client targetClient) {
        this.targetClient = targetClient;
    }
}
