package pl.warp.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
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
    private Channel outChannel;

    public synchronized int addClient(Client client) {
        lastId++;
        client.setId(lastId);
        clients.put(lastId, client);
        return lastId;
    }

    public synchronized void removeClient(int clientId) {
        clients.remove(clientId);
    }

    public synchronized Client getClient(int clientId) {
        return clients.get(clientId);
    }

    public synchronized void updateKeepAlive(int clientId) {
        Client client = clients.get(clientId);
        if (client != null) client.setLastKeepAlive(System.currentTimeMillis());
    }

    public synchronized void broadcast(ByteBuf msg) {
        for (Client client : clients.values())
            outChannel.writeAndFlush(new DatagramPacket(msg, client.getAddress()));
    }

    public synchronized void send(int clientId, ByteBuf msg) {
        Client client = clients.get(clientId);
        if (client != null) outChannel.writeAndFlush(new DatagramPacket(msg, client.getAddress()));
    }

    public void setOutChannel(Channel channel) {
        this.outChannel = channel;
    }
}
