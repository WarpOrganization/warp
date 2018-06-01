package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.IncomingMessageQueue;
import net.warpgame.engine.net.message.MessageProcessorsService;

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
    private Server server;
    private ComponentRegistry componentRegistry;
    private MessageProcessorsService messageProcessorsService;

    public ConnectionService(ComponentRegistry componentRegistry, MessageProcessorsService messageProcessorsService) {
        this.componentRegistry = componentRegistry;
        this.messageProcessorsService = messageProcessorsService;
    }

    void connect(InetSocketAddress address) {
        server = new Server(
                address,
                new IncomingMessageQueue(messageProcessorsService),
                new ConnectionStateHolder(componentRegistry.getRootComponent())
        );
        channel.writeAndFlush(
                new DatagramPacket(
                        Unpooled.buffer().writeInt(PacketType.PACKET_CONNECT).writeLong(System.currentTimeMillis()),
                        address));
    }

    public void sendPacket(ByteBuf packetData) {
        channel.writeAndFlush(new DatagramPacket(packetData, server.getAddress()));
    }

    public ByteBuf getHeader(int packetType, int initialCapacity) {
        ByteBuf byteBuf = Unpooled.buffer(initialCapacity + 16, 2048);
        byteBuf.writeInt(packetType);
        byteBuf.writeLong(System.currentTimeMillis());
        byteBuf.writeInt(clientId);
        return byteBuf;
    }

    void sendMessageConfirmationPacket(int dependencyId) {
        ByteBuf packet = getHeader(PacketType.PACKET_MESSAGE_CONFIRMATION, 4);
        packet.writeInt(dependencyId);
        sendPacket(packet);
    }

    void sendKeepAlivePacket() {
        channel.writeAndFlush(new DatagramPacket(getHeader(PacketType.PACKET_KEEP_ALIVE, 0), server.getAddress()));
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public InetSocketAddress getServerAddress() {
        return server.getAddress();
    }

    void setClientCredentials(int clientId, int clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Server getServer() {
        return server;
    }
}
