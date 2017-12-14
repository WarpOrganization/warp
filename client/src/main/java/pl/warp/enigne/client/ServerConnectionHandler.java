package pl.warp.enigne.client;

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
 * Created 27.11.2017
 */
public class ServerConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private boolean connected = false;
    private int clientId;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        int type = buffer.readInt();
        long timestamp = buffer.readLong();
        System.out.println("server sends message type: " + type);

        switch (type) {
            case PacketType.PACKET_CONNECTED:
                connected = true;
                clientId = buffer.readInt();
                break;
            case PacketType.PACKET_CONNECTION_REFUSED:
                System.out.println("Connection refused!");
                break;

            case PacketType.SCENE_STATE:

                break;
        }
    }


    private ByteBuf writeHeader(int packetType) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(packetType);
        buffer.writeLong(System.currentTimeMillis());
        buffer.writeInt(clientId);
        return buffer;
    }

    void sendKeepAlive(Channel channel, InetSocketAddress address) {
        if (connected)
            channel.writeAndFlush(new DatagramPacket(writeHeader(PacketType.PACKET_KEEP_ALIVE), address));
    }
}
