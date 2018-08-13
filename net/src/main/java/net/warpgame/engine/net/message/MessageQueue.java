package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;

import java.util.ArrayDeque;

/**
 * @author Hubertus
 * Created 30.05.2018
 */
@Service
public class MessageQueue {

    private ArrayDeque<MessageEnvelope> events = new ArrayDeque<>();
    private ArrayDeque<MessageEnvelope> resendQueue = new ArrayDeque<>();

    private static final int EVENT_RESEND_INTERVAL = 600;

    private MessageSender sender;

    public MessageQueue(MessageSender sender) {
        this.sender = sender;
    }

    public void pushEnvelope(MessageEnvelope messageEnvelope) {
        events.add(messageEnvelope);
    }

    public void update() {
        sendNewEvents();
        resendEvents();
    }

    private void sendNewEvents() {
        while (!events.isEmpty()) {
            MessageEnvelope envelope = events.pop();

            envelope.setDependencyId(
                    envelope.getTargetPeer().getDependencyIdGenerator().getNextDependencyId());
            envelope.getTargetPeer().addMessage(envelope);

            resendQueue.add(envelope);
            sender.sendMessage(envelope);
        }
    }

    private void resendEvents() {
        long currentTime = System.currentTimeMillis();
        while (!resendQueue.isEmpty()
                && (currentTime - resendQueue.getFirst().getSendTime() > EVENT_RESEND_INTERVAL
                || resendQueue.getFirst().isConfirmed())) {
            MessageEnvelope envelope = resendQueue.pop();
            if (!envelope.isConfirmed()) {
                resendQueue.add(envelope);
                sender.sendMessage(envelope);
            }
        }
    }
}