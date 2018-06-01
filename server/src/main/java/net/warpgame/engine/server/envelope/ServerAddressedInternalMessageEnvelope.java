package net.warpgame.engine.server.envelope;

import net.warpgame.engine.server.Client;

/**
 * @author Hubertus
 * Created 15.05.2018
 */
public class ServerAddressedInternalMessageEnvelope extends ServerAddresedEnvelope {
    private Client targetClient;

    public Client getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Client targetClient) {
        this.targetClient = targetClient;
    }

    @Override
    public boolean isInternal() {
        return true;
    }
}
