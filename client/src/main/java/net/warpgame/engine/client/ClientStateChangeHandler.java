package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.event.StateChangeRequestMessage;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ClientStateChangeHandler implements StateChangeHandler {

    private ConnectionService connectionService;

    public ClientStateChangeHandler(ConnectionService connectionService) {

        this.connectionService = connectionService;
    }

    @Override
    public void handleMessage(StateChangeRequestMessage message) {
        connectionService.getConnectionStateHolder().setPartnerRequestedConnectionState(message.getConnectionState());
    }
}
