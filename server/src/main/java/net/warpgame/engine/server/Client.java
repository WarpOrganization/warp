package net.warpgame.engine.server;

import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.event.receiver.MessageReceiver;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class Client extends Peer {
    private long lastKeepAlive;
    private int id;

    Client(InetSocketAddress address, MessageReceiver messageReceiver, ConnectionStateHolder connectionStateHolder) {
        super(address, messageReceiver, connectionStateHolder);
        lastKeepAlive = System.currentTimeMillis();
    }

    long getLastKeepAlive() {
        return lastKeepAlive;
    }

    void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
}
