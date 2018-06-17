package net.warpgame.engine.net;

import net.warpgame.engine.net.message.DependencyIdGenerator;
import net.warpgame.engine.net.message.IncomingMessageQueue;
import net.warpgame.engine.net.message.MessageEnvelope;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
public abstract class Peer {
    private Map<Integer, MessageEnvelope> messageConfirmations;
    private InetSocketAddress address;
    private IncomingMessageQueue incomingMessageQueue;
    private ClockSynchronizer clockSynchronizer;
    private ConnectionStateHolder connectionStateHolder;
    private DependencyIdGenerator dependencyIdGenerator;

    public Peer(InetSocketAddress address, IncomingMessageQueue incomingMessageQueue, ConnectionStateHolder connectionStateHolder) {
        this.address = address;
        this.incomingMessageQueue = incomingMessageQueue;
        this.connectionStateHolder = connectionStateHolder;
        messageConfirmations = new HashMap<>();
        clockSynchronizer = new ClockSynchronizer();
        dependencyIdGenerator = new DependencyIdGenerator();
    }

    public void addMessage(MessageEnvelope envelope) {
        messageConfirmations.put(envelope.getDependencyId(), envelope);
    }

    public void confirmMessage(int dependencyId) {
        MessageEnvelope envelope = messageConfirmations.get(dependencyId);
        if (envelope != null) {
            envelope.setConfirmed(true);
            messageConfirmations.remove(dependencyId);
        }
    }

    public IncomingMessageQueue getIncomingMessageQueue() {
        return incomingMessageQueue;
    }

    public void setIncomingMessageQueue(IncomingMessageQueue incomingMessageQueue) {
        this.incomingMessageQueue = incomingMessageQueue;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public ClockSynchronizer getClockSynchronizer() {
        return clockSynchronizer;
    }

    public void setClockSynchronizer(ClockSynchronizer clockSynchronizer) {
        this.clockSynchronizer = clockSynchronizer;
    }

    public ConnectionStateHolder getConnectionStateHolder() {
        return connectionStateHolder;
    }

    public void setConnectionStateHolder(ConnectionStateHolder connectionStateHolder) {
        this.connectionStateHolder = connectionStateHolder;
    }

    public ConnectionState getConnectionState() {
        return connectionStateHolder.getConnectionState();
    }

    public DependencyIdGenerator getDependencyIdGenerator() {
        return dependencyIdGenerator;
    }

    public void setDependencyIdGenerator(DependencyIdGenerator dependencyIdGenerator) {
        this.dependencyIdGenerator = dependencyIdGenerator;
    }
}