package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.net.PacketType;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 14.12.2017
 */

@Service
@RegisterTask(thread = "server")
public class SceneSynchronizationTask extends EngineTask {

    private static final int HEADER_SIZE = 4 + 8;
    private static final int SERIALIZED_COMPONENT_SIZE = 4 * (1 + 3 + 3 + 3 + 4);
    private static final int MAX_SERIALIZED_COMPONENTS = (2048 - HEADER_SIZE) / SERIALIZED_COMPONENT_SIZE;

    private static final int SCENE_SYNCHRONIZATION_INTERVAL = 50;


    private ComponentRegistry componentRegistry;
    private ClientRegistry clientRegistry;

    public SceneSynchronizationTask(ComponentRegistry componentRegistry, ClientRegistry clientRegistry) {
        this.componentRegistry = componentRegistry;
        this.clientRegistry = clientRegistry;
    }

    @Override
    protected void onInit() {
    }

    @Override
    protected void onClose() {

    }

    private int timer = SCENE_SYNCHRONIZATION_INTERVAL;

    @Override
    public void update(int delta) {
        timer -= delta;
        if (timer <= 0) {
            serializeScene();
            timer = SCENE_SYNCHRONIZATION_INTERVAL;
        }
    }

    private ArrayList<Component> components = new ArrayList<>();

    private void serializeScene() {
        components.clear();
        componentRegistry.getComponents(components);
        ByteBuf out = getBuffer();
        int counter = 0;

        for (Component c : components) {
            if (c.hasEnabledProperty(Property.getTypeId(TransformProperty.class))) {
                serializeComponent(c, out);
                counter++;
                if (counter >= MAX_SERIALIZED_COMPONENTS) {
                    clientRegistry.broadcast(out);
                    counter = 0;
                    out = getBuffer();
                }
            }
        }
        if (counter > 0) clientRegistry.broadcast(out);
    }

    private void serializeComponent(Component c, ByteBuf buffer) {
        TransformProperty transformProperty = c.getProperty(Property.getTypeId(TransformProperty.class));
        buffer.writeInt(c.getId());
        buffer.writeFloat(transformProperty.getTranslation().x);
        buffer.writeFloat(transformProperty.getTranslation().y);
        buffer.writeFloat(transformProperty.getTranslation().z);
        buffer.writeFloat(transformProperty.getRotation().x);
        buffer.writeFloat(transformProperty.getRotation().y);
        buffer.writeFloat(transformProperty.getRotation().z);
        buffer.writeFloat(transformProperty.getRotation().w);
    }

    private ByteBuf getBuffer() {
        return Unpooled
                .buffer(2048, 2048)
                .writeInt(PacketType.PACKET_SCENE_STATE)
                .writeLong(System.currentTimeMillis());
    }
}
