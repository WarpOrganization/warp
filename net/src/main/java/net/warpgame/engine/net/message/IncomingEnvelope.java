package net.warpgame.engine.net.message;

import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class IncomingEnvelope {
    private int messageType;
    private int messageDependencyId;
    private SerializationBuffer messageContent;
    private Peer sourcePeer;

    public IncomingEnvelope(Peer sourcePeer, int messageType, int messageDependencyId, SerializationBuffer messageContent) {
        this.messageType = messageType;
        this.messageDependencyId = messageDependencyId;
        this.messageContent = messageContent;
        this.sourcePeer = sourcePeer;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageDependencyId() {
        return messageDependencyId;
    }

    public void setMessageDependencyId(int messageDependencyId) {
        this.messageDependencyId = messageDependencyId;
    }

    public SerializationBuffer getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(SerializationBuffer messageContent) {
        this.messageContent = messageContent;
    }

    public Peer getSourcePeer() {
        return sourcePeer;
    }

    public void setSourcePeer(Peer sourcePeer) {
        this.sourcePeer = sourcePeer;
    }
}
