package net.warpgame.engine.server;

import net.warpgame.engine.net.event.AddressedEnvelope;

/**
 * @author Hubertus
 * Created 29.12.2017
 */
public class ServerAddressedEnvelope extends AddressedEnvelope {
    private Client targetClient;

    public Client getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Client targetClient) {
        this.targetClient = targetClient;
    }
}
