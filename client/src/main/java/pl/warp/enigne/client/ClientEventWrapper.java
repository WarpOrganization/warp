package pl.warp.enigne.client;

import pl.warp.net.RemoteEventWrapper;

/**
 * @author Hubertus
 * Created 19.12.2017
 */
public class ClientEventWrapper extends RemoteEventWrapper{

    ClientEventWrapper(byte[] eventData, int targetComponentId, int dependencyId) {
        super(eventData, targetComponentId, dependencyId);
    }
}
