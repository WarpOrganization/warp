package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.internalmessage.InternalMessage;
import net.warpgame.engine.net.internalmessage.InternalMessageHandler;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ClientInternalMessageHandler implements InternalMessageHandler {

    private ConnectionService connectionService;

    public ClientInternalMessageHandler(ConnectionService connectionService) {

        this.connectionService = connectionService;
    }

    @Override
    public void handleMessage(InternalMessage message) {
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
