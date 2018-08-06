package net.warpgame.engine.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.message.InternalMessageSource;
import net.warpgame.engine.net.message.MessageProcessorsService;
import net.warpgame.engine.net.message.MessageQueue;
import net.warpgame.engine.net.message.MessageSourcesService;

import java.net.InetSocketAddress;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@Profile("client")
@RegisterTask(thread = "client")
public class ClientTask extends EngineTask {

    private Config config;
    private ConnectionService connectionService;
    private final SerializedSceneHolder sceneHolder;
    private final ComponentRegistry componentRegistry;
    private MessageSourcesService messageSourcesService;
    private InternalMessageSource internalMessageSource;
    private MessageProcessorsService messageProcessorsService;
    private EventLoopGroup group = new NioEventLoopGroup();
    private MessageQueue messageQueue;

    private static final int KEEP_ALIVE_INTERVAL = 1000 * 5;
    private static final int CLOCK_SYNC_INTERVAL = 1000;
    private InetSocketAddress address;


    public ClientTask(Config config,
                      ConnectionService connectionService,
                      SerializedSceneHolder sceneHolder,
                      ComponentRegistry componentRegistry,
                      MessageQueue messageQueue,
                      MessageSourcesService messageSourcesService,
                      InternalMessageSource internalMessageSource,
                      MessageProcessorsService messageProcessorsService) {
        this.config = config;
        this.connectionService = connectionService;
        this.messageQueue = messageQueue;
        this.sceneHolder = sceneHolder;
        this.componentRegistry = componentRegistry;
        this.messageSourcesService = messageSourcesService;
        this.internalMessageSource = internalMessageSource;
        this.messageProcessorsService = messageProcessorsService;
    }

    @Override
    protected void onInit() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        address = SocketUtils.socketAddress(
                config.getValue("multiplayer.ip"),
                Integer.parseInt(System.getProperty("port", "" + config.getValue("multiplayer.port"))));
        setupConnection();
        connectionService.connect(address, messageProcessorsService);
    }

    private void setupConnection() {
        try {
            Bootstrap b = new Bootstrap();
            ServerConnectionHandler connectionHandler = new ServerConnectionHandler(
                    new IncomingPacketProcessor(
                            connectionService,
                            sceneHolder,
                            internalMessageSource));
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(connectionHandler);

            Channel channel = b.bind(0).sync().channel();
            connectionService.setChannel(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onClose() {
        group.shutdownGracefully();
    }


    private int counter = KEEP_ALIVE_INTERVAL;

    @Override
    public void update(int delta) {
        messageSourcesService.update();
        messageQueue.update();
        switch (connectionService.getServer().getConnectionState()) {
            case SYNCHRONIZING:
                syncClock(delta);
            case LIVE:
                keepAlive(delta);
                break;
        }
    }

    private int clockCounter = 0;

    private void syncClock(int delta) {
        clockCounter -= delta;
        if (clockCounter <= 0) {
            ByteBuf packet = connectionService.getHeader(PacketType.PACKET_CLOCK_SYNCHRONIZATION_REQUEST, 4);
            int randomInt = (int) (Math.random() * Integer.MAX_VALUE);
            packet.writeInt(randomInt);
            connectionService.getServer().getClockSynchronizer().startRequest(randomInt, System.currentTimeMillis());
            connectionService.sendPacket(packet);
            clockCounter = CLOCK_SYNC_INTERVAL;
        }
    }

    private void keepAlive(int delta) {
        counter -= delta;
        if (counter < 0) {
            counter = KEEP_ALIVE_INTERVAL;
            connectionService.sendKeepAlivePacket();
        }
    }
}
