package net.warpgame.engine.server;

import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.event.receiver.EventReceiver;
import net.warpgame.engine.server.envelope.ServerAddresedEnvelope;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Client {
    private InetSocketAddress address;
    private long lastKeepAlive;
    private Map<Integer, ServerAddresedEnvelope> eventConfirmations = new HashMap<>();
    private int id;
    private int eventDependencyIdCounter = 0;
    private EventReceiver eventReceiver;
    private ConnectionStateHolder connectionStateHolder;
    private ClockSynchronizer clockSynchronizer = new ClockSynchronizer();

    Client(InetSocketAddress address, EventReceiver eventReceiver, ConnectionStateHolder connectionStateHolder) {
        this.address = address;
        this.eventReceiver = eventReceiver;
        this.connectionStateHolder = connectionStateHolder;
        lastKeepAlive = System.currentTimeMillis();
    }

    long getLastKeepAlive() {
        return lastKeepAlive;
    }

    void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    InetSocketAddress getAddress() {
        return address;
    }

    void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    synchronized void confirmEvent(int eventDependencyId) {
        ServerAddresedEnvelope w = eventConfirmations.get(eventDependencyId);
        if (w != null) {
            w.setConfirmed(true);
            if (w.isShouldConfirm()) w.getTargetComponent().triggerEvent(w.getBouncerEvent());
            eventConfirmations.remove(eventDependencyId);
        }
    }

    synchronized void addEvent(ServerAddresedEnvelope addressedEnvelope) {
        eventConfirmations.put(addressedEnvelope.getDependencyId(), addressedEnvelope);
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    int getNextEventDependencyId() {
        eventDependencyIdCounter++;
        return eventDependencyIdCounter;
    }

    EventReceiver getEventReceiver() {
        return eventReceiver;
    }

    ClockSynchronizer getClockSynchronizer() {
        return clockSynchronizer;
    }

    void setClockSynchronizer(ClockSynchronizer clockSynchronizer) {
        this.clockSynchronizer = clockSynchronizer;
    }

    ConnectionState getConnectionState() {
        return connectionStateHolder.getConnectionState();
    }

    ConnectionStateHolder getConnectionStateHolder() {
        return connectionStateHolder;
    }
}
