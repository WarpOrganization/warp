package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionState;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Hubertus
 * Created 10.12.2017
 */
@Service
@Profile("server")
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
        toAdd.add(client);
        return lastId;
    }

    //TODO this probably should be used somewhere
    public synchronized void removeClient(int clientId) {
        toRemove.add(clientId);
    }

    public Client getClient(int clientId) {
        return clients.get(clientId);
    }

    public void updateKeepAlive(int clientId) {
        Client client = clients.get(clientId);
        if (client != null && client.getConnectionState() == ConnectionState.LIVE)
            client.setLastKeepAlive(System.currentTimeMillis());
    }

    public synchronized void broadcast(ByteBuf msg) {
        if (clients.size() > 1) msg.retain(clients.size() - 1);

        for (Client client : clients.values()) {
            if (client.getConnectionState() == ConnectionState.LIVE)
                connectionUtil.sendPacket(msg, client);
        }
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
