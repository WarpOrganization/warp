package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessage;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessageHandler;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
@Profile("client")
public class ClientInternalMessageHandler implements InternalMessageHandler {

    private ConnectionService connectionService;

    public ClientInternalMessageHandler(ConnectionService connectionService) {

        this.connectionService = connectionService;
    }

    @Override
    public void handleMessage(InternalMessage message, Peer sourcePeer) {
        switch (message.getMessageContent()) {
            case STATE_CHANGE_SYNCHRONIZING:
                connectionService
                        .getServer()
                        .getConnectionStateHolder()
                        .setPartnerRequestedConnectionState(ConnectionState.SYNCHRONIZING);
                break;
            case STATE_CHANGE_LIVE:
                connectionService
                        .getServer()
                        .getConnectionStateHolder()
                        .setPartnerRequestedConnectionState(ConnectionState.LIVE);
                break;
        }
    }
}
