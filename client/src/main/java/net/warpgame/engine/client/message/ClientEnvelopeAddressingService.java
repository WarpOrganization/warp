package net.warpgame.engine.client.message;

import net.warpgame.engine.client.ConnectionService;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.message.EnvelopeAddressingService;
import net.warpgame.engine.net.message.MessageEnvelope;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
@Service
public class ClientEnvelopeAddressingService extends EnvelopeAddressingService {

    private ConnectionService connectionService;

    public ClientEnvelopeAddressingService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @Override
    public void address(MessageEnvelope envelope, int targetClientId) {
        envelope.setTargetPeer(connectionService.getServer());
    }
}
