package net.warpgame.engine.server.envelope;

import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.server.Client;

/**
 * @author Hubertus
 * Created 15.05.2018
 */
public class ServerInternalMessageEnvelope extends ServerEnvelope{
    public ServerInternalMessageEnvelope(Event content) {
        super(content);
    }

    public ServerInternalMessageEnvelope(Event content, Client targetClient) {
        super(content, targetClient);
    }

    @Override
    public boolean isInternal() {
        return true;
    }
}
