package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.PacketType;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 19.12.2017
 */
@Service
public class ConnectionService {

    private Channel channel;
    private int clientId;
    private long clientSecret;//TODO implement
    private InetSocketAddress serverAddress;
    private ClockSynchronizer clockSynchronizer = new ClockSynchronizer();
    private ConnectionStateHolder connectionStateHolder;

    public ConnectionService(ComponentRegistry componentRegistry) {
        connectionStateHolder = new ConnectionStateHolder(componentRegistry.getComponent(0));
    }

    void connect(InetSocketAddress address) {
        this.serverAddress = address;
        channel.writeAndFlush(
                new DatagramPacket(
                        Unpooled.buffer().writeInt(PacketType.PACKET_CONNECT).writeLong(System.currentTimeMillis()),
                        address));
    }

    void sendPacket(ByteBuf packetData) {
        channel.writeAndFlush(new DatagramPacket(packetData, serverAddress));
    }

    ByteBuf getHeader(int packetType, int initialCapacity) {
        ByteBuf byteBuf = Unpooled.buffer(initialCapacity + 16, 2048);
        byteBuf.writeInt(packetType);
        byteBuf.writeLong(System.currentTimeMillis());
        byteBuf.writeInt(clientId);
        return byteBuf;
    }

    void confirmEvent(int dependencyId) {
        ByteBuf packet = getHeader(PacketType.PACKET_EVENT_CONFIRMATION, 4);
        packet.writeInt(dependencyId);
        sendPacket(packet);
    }

    void sendKeepAlive() {
        channel.writeAndFlush(new DatagramPacket(getHeader(PacketType.PACKET_KEEP_ALIVE, 0), serverAddress));
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    void setClientCredentials(int clientId, int clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    ClockSynchronizer getClockSynchronizer() {
        return clockSynchronizer;
    }

    public void setClockSynchronizer(ClockSynchronizer clockSynchronizer) {
        this.clockSynchronizer = clockSynchronizer;
    }

    ConnectionState getConnectionState() {
        return connectionStateHolder.getConnectionState();
    }

    ConnectionStateHolder getConnectionStateHolder() {
        return connectionStateHolder;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
