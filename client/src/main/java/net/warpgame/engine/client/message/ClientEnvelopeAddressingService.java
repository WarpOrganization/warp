package net.warpgame.engine.client.message;

import net.warpgame.engine.client.ConnectionService;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.EnvelopeAddressingService;
import net.warpgame.engine.net.message.MessageEnvelope;
import net.warpgame.engine.net.message.MessageQueue;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
@Service
@Profile("client")
public class ClientEnvelopeAddressingService extends EnvelopeAddressingService {

    private ConnectionService connectionService;
    private MessageQueue messageQueue;

    public ClientEnvelopeAddressingService(ConnectionService connectionService, MessageQueue messageQueue) {
        this.connectionService = connectionService;
        this.messageQueue = messageQueue;
    }

    @Override
    public void createEnvelope(byte[] serializedMessage, int targetPeerId) {
        MessageEnvelope envelope = new MessageEnvelope(serializedMessage, currentMessageType);
        envelope.setTargetPeer(connectionService.getServer());
        messageQueue.pushEnvelope(envelope);
    }

    @Override
    public void createEnvelope(byte[] serializedMessage, int targetPeerId, Consumer<Peer> confirmationConsumer) {
        MessageEnvelope envelope = new MessageEnvelope(serializedMessage, currentMessageType, confirmationConsumer);
        envelope.setTargetPeer(connectionService.getServer());
        messageQueue.pushEnvelope(envelope);
    }
}
