package net.warpgame.engine.net.message;

import net.warpgame.engine.net.Peer;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class EnvelopeAddressingService {

    protected int currentMessageType;

    public void setCurrentMessageType(int currentMessageType) {
        this.currentMessageType = currentMessageType;
    }

    /**
     * Creates envelope from given data and passes it for further processing and sending.
     * It is possible to broadcast a message as a sever by passing Client.ALL as targetPeerId
     *
     * @param serializedMessage serialized message object.
     * @param targetPeerId      id of receiving peer. Might be Client.ALL
     * @see net.warpgame.engine.core.serialization.Serialization
     */
    public abstract void createEnvelope(byte[] serializedMessage, int targetPeerId);

    /**
     * Creates envelope from given data and passes it for further processing and sending.
     * Correct processing of this message will be confirmed by invoking given confirmationConsumer.
     * It is possible to broadcast a message as a sever by passing Client.ALL as targetPeerId
     *
     * @param serializedMessage    serialized message object
     * @param targetPeerId         id of receiving peer. Might be Client.ALL
     * @param confirmationConsumer consumer to invoke upon receiving message confirmation
     * @see net.warpgame.engine.core.serialization.Serialization
     */
    public abstract void createEnvelope(byte[] serializedMessage, int targetPeerId, Consumer<Peer> confirmationConsumer);
}
