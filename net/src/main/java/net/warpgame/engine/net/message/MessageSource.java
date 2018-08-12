package net.warpgame.engine.net.message;

import java.util.ArrayDeque;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class MessageSource<T> {

    private ArrayDeque<T> messages = new ArrayDeque<>();
    private MessageQueue messageQueue;
    private EnvelopeAddressingService addressingService;

    public MessageSource(MessageQueue messageQueue, EnvelopeAddressingService addressingService) {
        this.messageQueue = messageQueue;
        this.addressingService = addressingService;
    }

    public synchronized void pushMessage(T message) {
        messages.add(message);
    }

    private synchronized T getNextMessage() {
        return messages.poll();
    }

    public void processMessages() {
        addressingService.setCurrentMessageType(getMessageType());
        T nextMessage = getNextMessage();
        while (nextMessage != null) {
            serializeMessage(nextMessage, addressingService);
            nextMessage = getNextMessage();
        }
    }

    abstract void serializeMessage(T message, EnvelopeAddressingService addressingService);

    abstract int getMessageType();
}
