package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Hubertus
 * Created 10.12.2017
 */
@Service
public class ClientRegistry {
    private Map<Integer, Client> clients = new HashMap<>();
    private int lastId = 0;

    private HashSet<Client> toAdd = new HashSet<>();
    private HashSet<Integer> toRemove = new HashSet<>();
    private ConnectionUtil connectionUtil;

    public ClientRegistry(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public synchronized int addClient(Client client) {
        lastId++;
        client.setId(lastId);
        //    clients.put(lastId, client);
        toAdd.add(client);
        return lastId;
    }

    public synchronized void removeClient(int clientId) {
        toRemove.add(clientId);
//        clients.remove(clientId);
    }

    public Client getClient(int clientId) {
        return clients.get(clientId);
    }

    public void updateKeepAlive(int clientId) {
        Client client = clients.get(clientId);
        if (client != null) client.setLastKeepAlive(System.currentTimeMillis());
    }

    public synchronized void broadcast(ByteBuf msg) {
        if (clients.size() > 1) msg.retain(clients.size() - 1);

        for (Client client : clients.values())
            connectionUtil.sendPacket(msg, client);
    }

    public Collection<Client> getClients() {
        return clients.values();
    }

    synchronized void update() {
        for (int id : toRemove) clients.remove(id);

        toRemove.clear();

        for (Client c : toAdd) clients.put(c.getId(), c);
        toAdd.clear();
    }
}
