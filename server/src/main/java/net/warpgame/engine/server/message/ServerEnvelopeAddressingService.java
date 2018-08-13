package net.warpgame.engine.server.message;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.EnvelopeAddressingService;
import net.warpgame.engine.net.message.MessageEnvelope;
import net.warpgame.engine.net.message.MessageQueue;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ClientRegistry;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
@Profile("server")
public class ServerEnvelopeAddressingService extends EnvelopeAddressingService {

    private ClientRegistry clientRegistry;
    private MessageQueue messageQueue;

    public ServerEnvelopeAddressingService(ClientRegistry clientRegistry, MessageQueue messageQueue) {
        this.clientRegistry = clientRegistry;
        this.messageQueue = messageQueue;
    }

    @Override
    public void createEnvelope(byte[] serializedMessage, int targetPeerId) {
        if (targetPeerId == Client.ALL)
            for (Client client : clientRegistry.getClients())
                createStandardEnvelope(serializedMessage, client);
        else createStandardEnvelope(serializedMessage, clientRegistry.getClient(targetPeerId));
    }

    private void createStandardEnvelope(byte[] serializedMessage, Client targetClient) {
        MessageEnvelope envelope = new MessageEnvelope(serializedMessage, currentMessageType);
        envelope.setTargetPeer(targetClient);
        messageQueue.pushEnvelope(envelope);
    }

    private void createConfirmableEnvelope(byte[] serializedMessage, Client targetClient, Consumer<Peer> confirmationConsumer) {
        MessageEnvelope envelope = new MessageEnvelope(serializedMessage, currentMessageType, confirmationConsumer);
        envelope.setTargetPeer(targetClient);
        messageQueue.pushEnvelope(envelope);
    }

    @Override
    public void createEnvelope(byte[] serializedMessage, int targetPeerId, Consumer<Peer> confirmationConsumer) {
        if (targetPeerId == Client.ALL)
            for (Client client : clientRegistry.getClients())
                createConfirmableEnvelope(serializedMessage, client, confirmationConsumer);
        else createConfirmableEnvelope(serializedMessage, clientRegistry.getClient(targetPeerId), confirmationConsumer);
    }
}
