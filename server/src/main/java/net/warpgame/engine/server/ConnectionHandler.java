package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.net.ConnectionState;
import net.warpgame.engine.net.ConnectionStateHolder;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.message.IncomingMessageQueue;
import net.warpgame.engine.net.message.MessageProcessorsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private ClientRegistry clientRegistry;
    private ComponentRegistry componentRegistry;
    private IncomingPacketProcessor packetProcessor;
    private ConnectionUtil connectionUtil;
    private StateChangeHandler stateChangeHandler;
    private MessageProcessorsService messageProcessorsService;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);


    ConnectionHandler(ClientRegistry clientRegistry,
                      ComponentRegistry componentRegistry,
                      IncomingPacketProcessor packetProcessor,
                      ConnectionUtil connectionUtil,
                      StateChangeHandler stateChangeHandler,
                      MessageProcessorsService messageProcessorsService) {
        this.clientRegistry = clientRegistry;
        this.componentRegistry = componentRegistry;
        this.packetProcessor = packetProcessor;
        this.connectionUtil = connectionUtil;
        this.stateChangeHandler = stateChangeHandler;
        this.messageProcessorsService = messageProcessorsService;
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
        Client c = new Client(
                address,
                new IncomingMessageQueue(messageProcessorsService),
                new ConnectionStateHolder(componentRegistry.getComponent(0)));
        int id = clientRegistry.addClient(c);
        ByteBuf packet = connectionUtil.getHeader(PacketType.PACKET_CONNECTED, 4);

        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED).writeInt(id), address));
//        componentRegistry.getComponent(0).triggerEvent(new ConnectedEvent(c));
        logger.info("Client connected from address " + address.toString());
        c.getConnectionStateHolder().setRequestedConnectionState(ConnectionState.SYNCHRONIZING);
        //TODO refactor internal messages
        //        eventQueue.pushEvent(new ServerInternalMessageEnvelope(new StateChangeRequestMessage(ConnectionState.SYNCHRONIZING)));
    }
}
