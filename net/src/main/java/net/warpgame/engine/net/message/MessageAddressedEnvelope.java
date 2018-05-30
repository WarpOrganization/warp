package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public class MessageAddressedEnvelope {
    private byte[] serializedMessage;
    private int dependencyId;
    private long sendTime;
    private boolean confirmed;
    private int messageType;
    private boolean shouldConfirm;


    public MessageAddressedEnvelope(byte[] serializedMessage, int messageType) {
        this.serializedMessage = serializedMessage;
        this.messageType = messageType;
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

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
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

    public boolean isShouldConfirm() {
        return shouldConfirm;
    }

    public void setShouldConfirm(boolean shouldConfirm) {
        this.shouldConfirm = shouldConfirm;
    }
}
