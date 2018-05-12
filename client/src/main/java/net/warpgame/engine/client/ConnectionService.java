package net.warpgame.engine.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.ClockSynchronizer;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.receiver.EventReceiver;
import net.warpgame.engine.net.event.receiver.InternalEventHandler;

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
    private EventLoopGroup group = new NioEventLoopGroup();
    private ClockSynchronizer clockSynchronizer = new ClockSynchronizer();
    private ConnectionState connectionState;

    public ConnectionService(SerializedSceneHolder sceneHolder,
                             ClientRemoteEventQueue eventQueue,
                             ComponentRegistry componentRegistry,
                             InternalEventHandler internalEventHandler) {

        try {
            Bootstrap b = new Bootstrap();
            ServerConnectionHandler connectionHandler = new ServerConnectionHandler(
                    sceneHolder,
                    this,
                    eventQueue,
                    new EventReceiver(componentRegistry, internalEventHandler));
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(connectionHandler);

            channel = b.bind(0).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connectionState = ConnectionState.CONNECTING;
    }

    void connect(InetSocketAddress address) {
        this.serverAddress = address;
        channel.writeAndFlush(
                new DatagramPacket(
                        Unpooled.buffer().writeInt(PacketType.PACKET_CONNECT).writeLong(System.currentTimeMillis()),
                        address));
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void sendPacket(ByteBuf packetData) {
        channel.writeAndFlush(new DatagramPacket(packetData, serverAddress));
    }

    public ByteBuf getHeader(int packetType, int initialCapacity) {
        ByteBuf byteBuf = Unpooled.buffer(initialCapacity + 16, 2048);
        byteBuf.writeInt(packetType);
        byteBuf.writeLong(System.currentTimeMillis());
        byteBuf.writeInt(clientId);
        return byteBuf;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    void sendKeepAlive() {
        channel.writeAndFlush(new DatagramPacket(getHeader(PacketType.PACKET_KEEP_ALIVE, 0), serverAddress));
    }

    public void shutdown() {
        group.shutdownGracefully();
    }

    public void confirmEvent(int dependencyId) {
        ByteBuf packet = getHeader(PacketType.PACKET_EVENT_CONFIRMATION, 4);
        packet.writeInt(dependencyId);
        sendPacket(packet);
    }

    public void setClientCredentials(int clientId, int clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public ClockSynchronizer getClockSynchronizer() {
        return clockSynchronizer;
    }

    public void setClockSynchronizer(ClockSynchronizer clockSynchronizer) {
        this.clockSynchronizer = clockSynchronizer;
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
    }
}
