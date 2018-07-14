package net.warpgame.engine.server.message;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.message.EnvelopeAddressingService;
import net.warpgame.engine.net.message.MessageEnvelope;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ClientRegistry;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
@Profile("server")
public class ServerEnvelopeAddressingService extends EnvelopeAddressingService {

    private ClientRegistry clientRegistry;

    public ServerEnvelopeAddressingService(ClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }

    @Override
    public void address(MessageEnvelope envelope, int targetClientId) {
        Client client = clientRegistry.getClient(targetClientId);
        envelope.setTargetPeer(client);
    }
}
