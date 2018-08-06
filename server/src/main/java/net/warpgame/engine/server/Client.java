package net.warpgame.engine.server;

import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.IncomingMessageQueue;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Client extends Peer {
    private long lastActivity;

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
}
