package pl.warp.enigne.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import pl.warp.net.PacketType;


/**
 * @author Hubertus
 * Created 27.11.2017
 */
public class ServerConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private boolean connected = false;
    private int clientId;
    private SerializedSceneHolder sceneHolder;

    public ServerConnectionHandler(SerializedSceneHolder sceneHolder) {
        this.sceneHolder = sceneHolder;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buffer = msg.content();
        int type = buffer.readInt();
        long timestamp = buffer.readLong();
//        System.out.println("server sends message type: " + type);

        switch (type) {
            case PacketType.PACKET_CONNECTED:
                connected = true;
                clientId = buffer.readInt();
                break;
            case PacketType.PACKET_CONNECTION_REFUSED:
                System.out.println("Connection refused!");
                break;

            case PacketType.PACKET_SCENE_STATE:
                sceneHolder.offerScene(timestamp, buffer);
                break;
        }
    }
}
