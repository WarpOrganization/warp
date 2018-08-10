package net.warpgame.engine.server;

import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.IdPool;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.IncomingMessageQueue;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Client extends Peer {
    private long lastActivity;
    private Map<Integer, IdPool> ownedPublicIdPools = new HashMap<>();

    Client(InetSocketAddress address, IncomingMessageQueue incomingMessageQueue, ConnectionStateHolder connectionStateHolder) {
        super(address, incomingMessageQueue, connectionStateHolder);
        lastActivity = System.currentTimeMillis();
    }

    long getLastActivity() {
        return lastActivity;
    }

    void setLastActivity(long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public void assignIdPool(IdPool pool) {
        ownedPublicIdPools.put(pool.getOffset(), pool);
    }
}
