package pl.warp.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import pl.warp.net.PacketType;

import java.net.InetSocketAddress;


/**
 * @author Hubertus
 * Created 26.11.2017
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;

    public ConnectionHandler(ClientRegistry clientRegistry, ConnectionUtil connectionUtil) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
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
                clientId = buffer.readInt();
                clientRegistry.updateKeepAlive(clientId);
                break;
            case PacketType.PACKET_EVENT:
                clientId = buffer.readInt();
                Client client = clientRegistry.getClient(clientId);
                if (client != null) {
                    int eventType = buffer.readInt();
                    int dependencyId = buffer.readInt();
                    int targetComponentId = buffer.readInt();
                    client.getEventReceiver().addEvent(buffer, targetComponentId, eventType, dependencyId, timestamp);
                }
                System.out.println("event received");

                break;
            case PacketType.PACKET_EVENT_CONFIRMATION:
                clientId = buffer.readInt();
                Client c = clientRegistry.getClient(clientId);
                int id = buffer.readInt();
                c.confirmEvent(id);
                connectionUtil.confirmEvent(id, c);
                System.out.println("event confirmation received");

                break;
        }
    }

    private ByteBuf writeHeader(int packetType) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(packetType);
        buffer.writeLong(System.currentTimeMillis());
        return buffer;
    }

    private void registerClient(Channel channel, InetSocketAddress address) {
        int id = clientRegistry.addClient(new Client(address));
        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED).writeInt(id), address));
    }
}
