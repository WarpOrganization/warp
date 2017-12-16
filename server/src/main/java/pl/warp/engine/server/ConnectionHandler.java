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

    public ConnectionHandler(ClientRegistry clientRegistry) {
        this.clientRegistry = clientRegistry;
    }

    //TODO protocol documentation
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        int type = buffer.readInt();
        long timestamp = buffer.readLong();
        System.out.println("server got a message type: " + type);

        switch (type) {
            case PacketType.PACKET_CONNECT:
                registerClient(ctx.channel(), msg.sender());
                break;
            case PacketType.PACKET_KEEP_ALIVE:
                clientRegistry.updateKeepAlive(buffer.readInt());
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
        int id = clientRegistry.addClient(new Client(channel, address));
        channel.writeAndFlush(
                new DatagramPacket(writeHeader(PacketType.PACKET_CONNECTED).writeInt(id), address));
    }
}
