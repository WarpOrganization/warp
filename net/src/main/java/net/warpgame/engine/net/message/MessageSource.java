package net.warpgame.engine.net.message;

import java.util.ArrayDeque;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
public abstract class MessageSource<T> {

    private ArrayDeque<T> messages = new ArrayDeque<>();
    private MessageQueue messageQueue;

    public MessageSource(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public synchronized void pushMessage(T message) {
        messages.add(message);
    }

    private synchronized T getNextMessage() {
        return messages.poll();
    }

    public void processMessages() {
        T nextMessage = getNextMessage();
        while (nextMessage != null) {
            messageQueue.pushEnvelope(toAddressedEnvelope(nextMessage));
            nextMessage = getNextMessage();
        }
    }

    abstract MessageEnvelope toAddressedEnvelope(T message);
}
