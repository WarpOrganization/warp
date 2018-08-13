package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.IncomingMessageQueue;
import net.warpgame.engine.net.message.InternalMessageSource;
import net.warpgame.engine.net.message.MessageProcessorsService;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessage;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessageContent;
import net.warpgame.engine.net.messagetypes.internalmessage.InternalMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private ClientRegistry clientRegistry;
    private IncomingPacketProcessor packetProcessor;
    private ConnectionUtil connectionUtil;
    private InternalMessageHandler internalMessageHandler;
    private MessageProcessorsService messageProcessorsService;
    private InternalMessageSource internalMessageSource;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private PacketType[] packetTypes = PacketType.values();

    ConnectionHandler(ClientRegistry clientRegistry,
                      IncomingPacketProcessor packetProcessor,
                      ConnectionUtil connectionUtil,
                      InternalMessageHandler internalMessageHandler,
                      MessageProcessorsService messageProcessorsService,
                      InternalMessageSource internalMessageSource) {
        this.clientRegistry = clientRegistry;
        this.packetProcessor = packetProcessor;
        this.connectionUtil = connectionUtil;
        this.internalMessageHandler = internalMessageHandler;
        this.messageProcessorsService = messageProcessorsService;
        this.internalMessageSource = internalMessageSource;
    }

    /**
     * Packet Header:
     * (int) PacketType, (long) timestamp, (int) clientId(except for PACKET_CONNECT)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf packet = msg.content();
        PacketType packetType = packetTypes[packet.readInt()];
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
        Client c = new Client(
                address,
                new IncomingMessageQueue(messageProcessorsService),
                new ConnectionStateHolder());
        int id = clientRegistry.addClient(c);
        c.getConnectionStateHolder().setPeerId(id);
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_CONNECTED, 4);

        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED.ordinal()).writeInt(id), address));
//        componentRegistry.getComponent(0).triggerEvent(new ConnectedEvent(c));
        logger.info("Client connected from address " + address.toString());
        c.getConnectionStateHolder().setRequestedConnectionState(ConnectionState.SYNCHRONIZING);
        internalMessageSource.pushMessage(new InternalMessage(InternalMessageContent.STATE_CHANGE_SYNCHRONIZING, c.getId()));
    }
}
