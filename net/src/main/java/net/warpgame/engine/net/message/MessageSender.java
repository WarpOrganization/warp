package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class MessageSender {

    /**
     * Updates send time and sends message
     * @param addressedEnvelope message to send
     */
    void sendMessage(MessageEnvelope addressedEnvelope){
        addressedEnvelope.setSendTime(System.currentTimeMillis());
        send(addressedEnvelope);
    }

    /**
     * Sends message without updating send time
     * @param addressedEnvelope message to send
     */
    public abstract void send(MessageEnvelope addressedEnvelope);

}
