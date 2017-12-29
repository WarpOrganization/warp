package pl.warp.engine.server;

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

    Client(InetSocketAddress address) {
        this.address = address;
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
        if (w != null) w.setConfirmed(true);
    }

    synchronized void addEvent(ServerAddressedEnvelope addressedEnvelope) {
        eventConfirmations.put(addressedEnvelope.getDependencyId(), addressedEnvelope);
    }

    synchronized void removeEvent(int eventDependencyId) {
        eventConfirmations.remove(eventDependencyId);
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
}
