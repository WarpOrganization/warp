package pl.warp.engine.server;

import pl.warp.net.RemoteEventWrapper;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public class ServerEventWrapper extends RemoteEventWrapper{

    private Client targetClient;

    public Client getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Client targetClient) {
        this.targetClient = targetClient;
    }
}
