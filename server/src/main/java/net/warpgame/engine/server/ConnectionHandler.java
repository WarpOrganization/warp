package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.InternalMessageHandler;
import net.warpgame.engine.net.event.receiver.EventReceiver;

import java.net.InetSocketAddress;


/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public ClientRegistry clientRegistry;
    private ComponentRegistry componentRegistry;
    private IncomingPacketProcessor packetProcessor;
    private ConnectionUtil connectionUtil;
    private InternalMessageHandler internalMessageHandler;

    public ConnectionHandler(ClientRegistry clientRegistry,
                             ComponentRegistry componentRegistry,
                             IncomingPacketProcessor packetProcessor,
                             ConnectionUtil connectionUtil,
                             InternalMessageHandler internalMessageHandler) {
        this.clientRegistry = clientRegistry;
        this.componentRegistry = componentRegistry;
        this.packetProcessor = packetProcessor;
        this.connectionUtil = connectionUtil;
        this.internalMessageHandler = internalMessageHandler;
    }

    /**
     * Packet Header:
     * (int) PacketType, (long) timestamp, (int) clientId(except for PACKET_CONNECT)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf packet = msg.content();
        int packetType = packet.readInt();
        long timeStamp = packet.readLong();
        if (packetType == PacketType.PACKET_CONNECT) registerClient(ctx.channel(), msg.sender());
        else packetProcessor.processPacket(packetType, timeStamp, packet);
    }

    private ByteBuf writeHeader(int packetType) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(packetType);
        buffer.writeLong(System.currentTimeMillis());
        return buffer;
    }

    private void registerClient(Channel channel, InetSocketAddress address) {
        Client c = new Client(address, new EventReceiver(componentRegistry, internalMessageHandler));
        int id = clientRegistry.addClient(c);
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_CONNECTED, 4);

        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED).writeInt(id), address));
        componentRegistry.getComponent(0).triggerEvent(new ConnectedEvent(c));
        //TODO issue state change to SYCHRONIZING
    }
}
