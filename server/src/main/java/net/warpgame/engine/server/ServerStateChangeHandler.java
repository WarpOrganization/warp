package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.event.StateChangeRequestMessage;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ServerStateChangeHandler implements StateChangeHandler {
    private ClientRegistry clientRegistry;

    public ServerStateChangeHandler(ClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }

    @Override
    public void handleMessage(StateChangeRequestMessage message) {
        Client targetClient = clientRegistry.getClient(message.getTargetId());
        if (targetClient != null)
            targetClient.getConnectionStateHolder().setPartnerRequestedConnectionState(message.getConnectionState());
    }
}
