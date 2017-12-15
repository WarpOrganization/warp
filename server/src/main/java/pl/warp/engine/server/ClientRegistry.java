package pl.warp.engine.server;

import io.netty.buffer.ByteBuf;
import pl.warp.engine.core.context.service.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hubertus
 * Created 10.12.2017
 */
@Service
public class ClientRegistry {
    private Map<Integer, Client> clients = new HashMap<>();
    private int lastId = 0;


    public synchronized int addClient(Client client) {
        lastId++;
        clients.put(lastId, client);
        return lastId;
    }

    public synchronized void removeClient(int clientId) {
        clients.remove(clientId);
    }

    public synchronized Client getClient(int clientId) {
        return clients.get(clientId);
    }

    public synchronized void updateKeepAlve(int clientId) {
        Client client = clients.get(clientId);
        if (client != null) client.setLastKeepAlive(System.currentTimeMillis());
    }

    public synchronized void broadcast(ByteBuf msg) {
        for (Client client : clients.values()) client.getChannel().writeAndFlush(msg);
    }

    public synchronized void send(int clientId, ByteBuf msg) {
        Client client = clients.get(clientId);
        if (client != null) client.getChannel().writeAndFlush(msg);
    }

}
