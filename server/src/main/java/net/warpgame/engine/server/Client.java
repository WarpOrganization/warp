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
    private long lastKeepAlive;

    Client(InetSocketAddress address, IncomingMessageQueue incomingMessageQueue, ConnectionStateHolder connectionStateHolder) {
        super(address, incomingMessageQueue, connectionStateHolder);
        lastKeepAlive = System.currentTimeMillis();
    }

    long getLastKeepAlive() {
        return lastKeepAlive;
    }

    void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }
}
