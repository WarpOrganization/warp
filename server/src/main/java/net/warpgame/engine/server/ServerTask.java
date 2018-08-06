package net.warpgame.engine.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.net.internalmessage.InternalMessageHandler;
import net.warpgame.engine.net.message.InternalMessageSource;
import net.warpgame.engine.net.message.MessageProcessorsService;
import net.warpgame.engine.net.message.MessageQueue;
import net.warpgame.engine.net.message.MessageSourcesService;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@Profile("server")
@RegisterTask(thread = "server")
public class ServerTask extends EngineTask {

    private static int PORT = Integer.parseInt(System.getProperty("port", "6688"));


    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;
    private ComponentRegistry componentRegistry;
    private IncomingPacketProcessor packetProcessor;
    private InternalMessageHandler internalMessageHandler;
    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel outChannel;
    private MessageQueue messageQueue;
    private MessageProcessorsService messageProcessorsService;
    private InternalMessageSource internalMessageSource;
    private MessageSourcesService messageSourcesService;

    public ServerTask(ClientRegistry clientRegistry,
                      ConnectionUtil connectionUtil,
                      ComponentRegistry componentRegistry,
                      IncomingPacketProcessor packetProcessor,
                      InternalMessageHandler internalMessageHandler,
                      MessageQueue messageQueue,
                      MessageProcessorsService messageProcessorsService,
                      InternalMessageSource internalMessageSource,
                      MessageSourcesService messageSourcesService) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
        this.componentRegistry = componentRegistry;
        this.packetProcessor = packetProcessor;
        this.internalMessageHandler = internalMessageHandler;
        this.messageQueue = messageQueue;
        this.messageProcessorsService = messageProcessorsService;
        this.internalMessageSource = internalMessageSource;
        this.messageSourcesService = messageSourcesService;
    }

    @Override
    protected void onInit() {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ConnectionHandler(
                            clientRegistry,
                            componentRegistry,
                            packetProcessor,
                            connectionUtil,
                            internalMessageHandler,
                            messageProcessorsService,
                            internalMessageSource
                    ));


            outChannel = b.bind(PORT).sync().channel();
            connectionUtil.setOutChannel(outChannel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onClose() {
        group.shutdownGracefully();
    }


    @Override
    public void update(int delta) {
        clientRegistry.update();
        messageSourcesService.update();
        messageQueue.update();
    }

}