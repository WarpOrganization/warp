package net.warpgame.engine.server;

import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.event.receiver.EventReceiver;

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
    private Map<Integer, ServerAddressedEnvelope> eventConfirmations = new HashMap<>();
    private int id;
    private int eventDependencyIdCounter = 0;
    private EventReceiver eventReceiver;
    private ClockSynchronizer clockSynchronizer = new ClockSynchronizer();
    private ConnectionState connectionState;

    Client(InetSocketAddress address, EventReceiver eventReceiver) {
        this.address = address;
        this.eventReceiver = eventReceiver;
        lastKeepAlive = System.currentTimeMillis();
    }

    long getLastKeepAlive() {
        return lastKeepAlive;
    }

    void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    synchronized void confirmEvent(int eventDependencyId) {
        ServerAddressedEnvelope w = eventConfirmations.get(eventDependencyId);
        if (w != null) {
            w.setConfirmed(true);
            if (w.isShouldConfirm()) w.getTargetComponent().triggerEvent(w.getBouncerEvent());
            eventConfirmations.remove(eventDependencyId);
        }
    }

    synchronized void addEvent(ServerAddressedEnvelope addressedEnvelope) {
        eventConfirmations.put(addressedEnvelope.getDependencyId(), addressedEnvelope);
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    int getNextEventDependencyId() {
        eventDependencyIdCounter++;
        return eventDependencyIdCounter;
    }

    public EventReceiver getEventReceiver() {
        return eventReceiver;
    }

    public ClockSynchronizer getClockSynchronizer() {
        return clockSynchronizer;
    }

    public void setClockSynchronizer(ClockSynchronizer clockSynchronizer) {
        this.clockSynchronizer = clockSynchronizer;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
}
