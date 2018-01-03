package pl.warp.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.net.PacketType;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 29.12.2017
 */
@Service
public class ConnectionUtil {

    private Channel outChannel;

    public ByteBuf getHeader(int packetType, int initialCapacity) {
        ByteBuf byteBuf = Unpooled.buffer(initialCapacity + 12, 2048);
        byteBuf.writeInt(packetType);
        byteBuf.writeLong(System.currentTimeMillis());
        return byteBuf;
    }

    public void sendPacket(ByteBuf content, Client target) {
        sendPacket(content, target.getAddress());
    }

    public void sendPacket(ByteBuf content, InetSocketAddress targetAddress) {
        outChannel.writeAndFlush(new DatagramPacket(content, targetAddress));
    }

    public Channel getOutChannel() {
        return outChannel;
    }

    public void setOutChannel(Channel outChannel) {
        this.outChannel = outChannel;
    }

    public void confirmEvent(int dependencyId, Client client) {
        ByteBuf packet = getHeader(PacketType.PACKET_EVENT_CONFIRMATION, 4);
        packet.writeInt(dependencyId);
    }
}
