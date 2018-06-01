package net.warpgame.engine.net.message;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.net.Peer;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class IncomingMessageQueue {

    private int minDependencyId = 1;
    private PriorityQueue<IncomingEnvelope> messageQueue = new PriorityQueue<>(new IncomingEnvelopeComparator());
    private MessageProcessorsService messageProcessorsService;


    public IncomingMessageQueue(MessageProcessorsService messageProcessorsService) {


        this.messageProcessorsService = messageProcessorsService;
    }

    public synchronized void addMessage(Peer sourcePeer, int messageType, int dependencyId, ByteBuf content) {
        if (checkDependency(dependencyId)) {
            messageQueue.add(new IncomingEnvelope(sourcePeer, messageType, dependencyId, content));
        }
        processIncomingMessages();
    }

    private void processIncomingMessages() {
        while (!messageQueue.isEmpty() && minDependencyId == messageQueue.element().getMessageDependencyId()) {
            IncomingEnvelope envelope = messageQueue.poll();
            MessageProcessor messageProcessor = messageProcessorsService.getMessageProcessor(envelope.getMessageType());
            if (messageProcessor == null) throw new UnknownMessageTypeException(envelope.getMessageType());
            messageProcessor.processMessage(envelope.getSourcePeer(), envelope.getMessageContent());
            minDependencyId++;
        }
    }

    private boolean checkDependency(int dependencyId) {
        if (minDependencyId > dependencyId) return false;
        if (messageQueue.isEmpty()) return true;

        Iterator<IncomingEnvelope> iterator = messageQueue.iterator();

        int d = iterator.next().getMessageDependencyId();
        while (iterator.hasNext() && d < dependencyId) {
            d = iterator.next().getMessageDependencyId();
            if (d == dependencyId) return false;
        }
        return true;
    }
}
