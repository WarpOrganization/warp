package net.warpgame.engine.net.message;

import net.warpgame.engine.net.Peer;

import java.util.function.Consumer;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public class MessageEnvelope {
    private byte[] serializedMessage;
    private int dependencyId;
    private long nextResendTime;
    private boolean confirmed;
    private int messageType;
    private Peer targetPeer;
    private boolean wantsConfirmation;
    private Consumer<Peer> confirmationConsumer;

    public MessageEnvelope(byte[] serializedMessage, int messageType) {
        this.serializedMessage = serializedMessage;
        this.messageType = messageType;
        this.wantsConfirmation = false;
    }

    public MessageEnvelope(byte[] serializedMessage, int messageType, Consumer<Peer> confirmationConsumer) {
        this.serializedMessage = serializedMessage;
        this.messageType = messageType;
        this.confirmationConsumer = confirmationConsumer;
        this.wantsConfirmation = true;
    }

    public void confirm(){
        confirmed = true;
        if(wantsConfirmation)
            confirmationConsumer.accept(targetPeer);
    }

    public byte[] getSerializedMessage() {
        return serializedMessage;
    }

    public void setSerializedMessage(byte[] serializedMessage) {
        this.serializedMessage = serializedMessage;
    }

    public int getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(int dependencyId) {
        this.dependencyId = dependencyId;
    }

    public long getNextResendTime() {
        return nextResendTime;
    }

    public void setNextResendTime(long nextResendTime) {
        this.nextResendTime = nextResendTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public Peer getTargetPeer() {
        return targetPeer;
    }

    public void setTargetPeer(Peer targetPeer) {
        this.targetPeer = targetPeer;
    }

    public boolean isWantsConfirmation() {
        return wantsConfirmation;
    }

    public void setWantsConfirmation(boolean wantsConfirmation) {
        this.wantsConfirmation = wantsConfirmation;
    }

    public Consumer<Peer> getConfirmationConsumer() {
        return confirmationConsumer;
    }

    public void setConfirmationConsumer(Consumer<Peer> confirmationConsumer) {
        this.confirmationConsumer = confirmationConsumer;
    }
}
