package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.receiver.EventReceiver;

import java.net.InetSocketAddress;


/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;
    private ComponentRegistry componentRegistry;

    public ConnectionHandler(ClientRegistry clientRegistry,
                             ConnectionUtil connectionUtil,
                             ComponentRegistry componentRegistry) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
        this.componentRegistry = componentRegistry;
    }

    //TODO protocol documentation
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        int type = buffer.readInt();
        long timestamp = buffer.readLong();
        System.out.println("server got a message type: " + type);
        int clientId;
        switch (type) {
            case PacketType.PACKET_CONNECT:
                registerClient(ctx.channel(), msg.sender());
                break;
            case PacketType.PACKET_KEEP_ALIVE:
                clientRegistry.updateKeepAlive(buffer.readInt());
                break;
            case PacketType.PACKET_EVENT:
                handleEvent(timestamp, buffer);
                break;
            case PacketType.PACKET_EVENT_CONFIRMATION:
                handleEventConfirmation(buffer.readInt(), buffer.readInt());
                break;
            case PacketType.PACKET_CLOCK_SYNCHRONIZATION_REQUEST:
                handleClockSynchronizationRequest(buffer.readInt(), buffer.readInt());
                break;
            case PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE:
                handleClockSynchronizationResponse(buffer.readInt(), timestamp, buffer.readInt());
                break;

        }
    }

    private void handleEvent(long timestamp, ByteBuf buffer) {
        int clientId = buffer.readInt();
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            int eventType = buffer.readInt();
            int dependencyId = buffer.readInt();
            int targetComponentId = buffer.readInt();
            client.getEventReceiver().addEvent(buffer, targetComponentId, eventType, dependencyId, timestamp);
            connectionUtil.confirmEvent(dependencyId, client);
        }
    }

    private void handleEventConfirmation(int clientId, int eventDependencyId) {
        Client c = clientRegistry.getClient(clientId);
        c.confirmEvent(eventDependencyId);
    }

    private void handleClockSynchronizationRequest(int clientId, int requestId) {
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            client.getClockSynchronizer().startRequest(requestId, System.currentTimeMillis());

            ByteBuf packet =
                    connectionUtil.getHeader(PacketType.PACKET_CLOCK_SYNCHRONIZATION_RESPONSE, 4);
            packet.writeInt(requestId);
            connectionUtil.sendPacket(packet, client);
        }
    }

    private void handleClockSynchronizationResponse(int clientId, long timestamp, int requestId) {
        Client client = clientRegistry.getClient(clientId);
        if (client != null) {
            ClockSynchronizer synchronizer = client.getClockSynchronizer();
            synchronizer.synchronize(timestamp, requestId);
            if (synchronizer.getFinishedSynchronizations() >= 3) System.out.println(synchronizer.getDelta());
        }
    }

    private ByteBuf writeHeader(int packetType) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(packetType);
        buffer.writeLong(System.currentTimeMillis());
        return buffer;
    }

    private void registerClient(Channel channel, InetSocketAddress address) {
        Client c = new Client(address, new EventReceiver(componentRegistry));
        int id = clientRegistry.addClient(c);
        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED).writeInt(id), address));
        componentRegistry.getComponent(0).triggerEvent(new ConnectedEvent(c));
    }
}
