package pl.warp.engine.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.ComponentRegistry;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 26.11.2017
 */
@Service
@RegisterTask(thread = "server")
public class ServerTask extends EngineTask {

    private static int PORT = Integer.parseInt(System.getProperty("port", "6688"));
    private static int MAX_SERIALIZED_COMPONENTS = (2048 - 1) / 4 * (1 + 3 + 3 + 3 + 4);

    private ComponentRegistry componentRegistry;

    private ClientRegistry clientRegistry;

    public ServerTask(ComponentRegistry componentRegistry, ClientRegistry clientRegistry){
        this.componentRegistry = componentRegistry;
        this.clientRegistry = clientRegistry;
    }

    @Override
    protected void onInit() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ConnectionHandler(clientRegistry));
            b.bind(PORT).sync().channel().closeFuture().await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void onClose() {

    }


    @Override
    public void update(int delta) {

    }

    private ArrayList<Component> components = new ArrayList<>();

//    private byte[] serializeScene() {
//        ByteBuf buffer = Unpooled.buffer(2048);
//        generateHeader(buffer);
//        int serializedCounter = 0;
//        components.clear();
//        componentRegistry.getComponents(components);
//        for (Component c : components) {
//            if (c.hasEnabledProperty(TransformProperty.NAME)) {
//                serializeComponent(c, buffer);
//                serializedCounter++;
//            }
//        }
//    }

    private void serializeComponent(Component c, ByteBuf buffer){
        TransformProperty transformProperty = c.getProperty(TransformProperty.NAME);
        buffer.writeInt(c.getId());
        buffer.writeFloat(transformProperty.getTranslation().x);
        buffer.writeFloat(transformProperty.getTranslation().y);
        buffer.writeFloat(transformProperty.getTranslation().z);
        buffer.writeFloat(transformProperty.getRotation().x);
        buffer.writeFloat(transformProperty.getRotation().y);
        buffer.writeFloat(transformProperty.getRotation().z);
        buffer.writeFloat(transformProperty.getRotation().w);
    }



    private void broadcastScene(ByteBuf buffer){

    }
}
