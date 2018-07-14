package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.ConnectionTools;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.IncomingMessageQueue;
import net.warpgame.engine.net.message.MessageProcessorsService;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 19.12.2017
 */
@Service
@Profile("client")
public class ConnectionService implements ConnectionTools {

    private Channel channel;
    private int clientId;
    private long clientSecret;//TODO implement
    private Server server;
    private ComponentRegistry componentRegistry;

    public ConnectionService(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    void connect(InetSocketAddress address, MessageProcessorsService messageProcessorsService) {
        server = new Server(
                address,
                new IncomingMessageQueue(messageProcessorsService),
                new ConnectionStateHolder(componentRegistry.getRootComponent())
        );
        channel.writeAndFlush(
                new DatagramPacket(
                        Unpooled.buffer().writeInt(PacketType.PACKET_CONNECT.ordinal()).writeLong(System.currentTimeMillis()),
                        address));
    }

    public void sendPacket(ByteBuf packetData) {
        channel.writeAndFlush(new DatagramPacket(packetData, server.getAddress()));
    }

    public ByteBuf getHeader(PacketType packetType, int initialCapacity) {
        ByteBuf byteBuf = Unpooled.buffer(initialCapacity + 16, 2048);
        byteBuf.writeInt(packetType.ordinal());
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

    @Override
    public int getPeerId() {
        return clientId;
    }
}
