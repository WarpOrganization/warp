package net.warpgame.engine.client;

import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.IncomingMessageQueue;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
public class Server extends Peer {
    public Server(InetSocketAddress address, IncomingMessageQueue messageReceiver, ConnectionStateHolder connectionStateHolder) {
        super(address, messageReceiver, connectionStateHolder);
    }
}
