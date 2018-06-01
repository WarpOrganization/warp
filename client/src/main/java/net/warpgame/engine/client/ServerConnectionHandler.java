package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;


/**
 * @author Hubertus
 * Created 27.11.2017
 */
public class ServerConnectionHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private IncomingPacketProcessor packetProcessor;


    public ServerConnectionHandler(IncomingPacketProcessor packetProcessor) {
        this.packetProcessor = packetProcessor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf packet = msg.content();
        packetProcessor.processPacket(packet);
    }
}
