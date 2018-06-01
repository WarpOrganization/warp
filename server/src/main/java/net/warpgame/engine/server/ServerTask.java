package net.warpgame.engine.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.net.event.StateChangeHandler;
import net.warpgame.engine.net.message.MessageProcessorsService;
import net.warpgame.engine.net.message.MessageQueue;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@RegisterTask(thread = "server")
public class ServerTask extends EngineTask {

    private static int PORT = Integer.parseInt(System.getProperty("port", "6688"));


    private ClientRegistry clientRegistry;
    private ConnectionUtil connectionUtil;
    private ComponentRegistry componentRegistry;
    private IncomingPacketProcessor packetProcessor;
    private StateChangeHandler stateChangeHandler;
    private EventLoopGroup group = new NioEventLoopGroup();
    private Channel outChannel;
    private MessageQueue messageQueue;
    private MessageProcessorsService messageProcessorsService;

    public ServerTask(ClientRegistry clientRegistry,
                      ConnectionUtil connectionUtil,
                      ComponentRegistry componentRegistry,
                      IncomingPacketProcessor packetProcessor,
                      StateChangeHandler stateChangeHandler,
                      MessageQueue messageQueue,
                      MessageProcessorsService messageProcessorsService) {
        this.clientRegistry = clientRegistry;
        this.connectionUtil = connectionUtil;
        this.componentRegistry = componentRegistry;
        this.packetProcessor = packetProcessor;
        this.stateChangeHandler = stateChangeHandler;
        this.messageQueue = messageQueue;
        this.messageProcessorsService = messageProcessorsService;
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
                            stateChangeHandler,
                            messageProcessorsService
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
        messageQueue.update();
    }

}