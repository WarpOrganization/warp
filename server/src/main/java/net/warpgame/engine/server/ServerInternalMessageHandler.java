package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageHandler;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ServerInternalMessageHandler implements InternalMessageHandler {
    private ClientRegistry clientRegistry;

    public ServerInternalMessageHandler(ClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }

    @Override
    public void handleMessage(InternalMessage message, Peer sourcePeer) {
        Client targetClient = (Client) sourcePeer;
        if (targetClient != null)
            switch (message.getMessageContent()) {
                case STATE_CHANGE_SYNCHRONIZING:
                    targetClient.getConnectionStateHolder().setPartnerRequestedConnectionState(ConnectionState.SYNCHRONIZING);
                    break;
                case STATE_CHANGE_LIVE:
                    targetClient.getConnectionStateHolder().setPartnerRequestedConnectionState(ConnectionState.LIVE);
                    break;
            }
    }
}
