package pl.warp.enigne.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;
import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;
import pl.warp.net.PacketType;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@RegisterTask(thread = "client")
public class ClientTask extends EngineTask {

    private Config config;

    private static int KEEP_ALIVE_INTERVAL = 1000 * 5;
    private InetSocketAddress address;

    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel ch;
    private ServerConnectionHandler connectionHandler;

    public ClientTask(Config config) {
        this.config = config;
    }

    @Override
    protected void onInit() {
        try {
            Bootstrap b = new Bootstrap();
            connectionHandler = new ServerConnectionHandler();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(connectionHandler);

            ch = b.bind(0).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        address = SocketUtils.socketAddress(
                config.getValue("multiplayer.ip"),
                Integer.parseInt(System.getProperty("port", "" + config.getValue("multiplayer.port"))));
        ch.writeAndFlush(
                new DatagramPacket(
                        Unpooled.buffer().writeInt(PacketType.PACKET_CONNECT).writeLong(System.currentTimeMillis()),
                        address));
    }

    @Override
    protected void onClose() {
        group.shutdownGracefully();
    }


    private int counter = KEEP_ALIVE_INTERVAL;

    @Override
    public void update(int delta) {
        counter -= delta;
        if (counter < 0) {
            counter = KEEP_ALIVE_INTERVAL;
            connectionHandler.sendKeepAlive(ch, address);
        }
    }


}
