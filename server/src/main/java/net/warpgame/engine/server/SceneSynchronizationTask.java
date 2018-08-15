package net.warpgame.engine.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.net.PacketType;
import net.warpgame.engine.net.SerializationType;
import net.warpgame.engine.net.StateSynchronizerProperty;
import net.warpgame.engine.physics.FullPhysicsProperty;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 14.12.2017
 */

@Service
@Profile("server")
@RegisterTask(thread = "server")
public class SceneSynchronizationTask extends EngineTask {
    /**
     * @see PacketType
     */
    private static final int HEADER_SIZE = 4 + 8;

    private static final int MAX_PACKET_SIZE = 576;

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
        int spaceLeft = MAX_PACKET_SIZE;
        spaceLeft -= HEADER_SIZE;
        for (Component c : components) {
            if (c.hasEnabledProperty(Property.getTypeId(StateSynchronizerProperty.class))) {
                StateSynchronizerProperty stateSynchronizerProperty =
                        c.getProperty(Property.getTypeId(StateSynchronizerProperty.class));
                if (stateSynchronizerProperty.getSerializationType() == SerializationType.POSITION) {
                    out.writeInt(SerializationType.POSITION.ordinal());
                    serializeComponentPosition(c, out);
                    spaceLeft -= SerializationType.Size.POSITION_SIZE;
                } else if (stateSynchronizerProperty.getSerializationType() == SerializationType.POSITION_AND_VELOCITY) {
                    out.writeInt(SerializationType.POSITION_AND_VELOCITY.ordinal());
                    serializeComponentPosition(c, out);
                    serializeComponentVelocity(c, out);
                    spaceLeft -= SerializationType.Size.POSITION_AND_VELOCITY_SIZE;
                }

                if (spaceLeft < SerializationType.Size.POSITION_AND_VELOCITY_SIZE) {
                    clientRegistry.broadcast(out);
                    spaceLeft = MAX_PACKET_SIZE - HEADER_SIZE;
                    out = getBuffer();
                }
            }

        }
        if (spaceLeft < MAX_PACKET_SIZE - HEADER_SIZE) clientRegistry.broadcast(out);
    }

    private void serializeComponentPosition(Component c, ByteBuf buffer) {
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

    private Vector3f velocity = new Vector3f();
    private Vector3f angularVelocity = new Vector3f();

    private void serializeComponentVelocity(Component c, ByteBuf buffer) {
        FullPhysicsProperty physicsProperty = c.getProperty(Property.getTypeId(FullPhysicsProperty.class));
        physicsProperty.getVelocity(velocity);
        buffer.writeFloat(velocity.x);
        buffer.writeFloat(velocity.y);
        buffer.writeFloat(velocity.z);

        physicsProperty.getAngularVelocity(angularVelocity);
        buffer.writeFloat(angularVelocity.x);
        buffer.writeFloat(angularVelocity.y);
        buffer.writeFloat(angularVelocity.z);
    }

    private ByteBuf getBuffer() {
        return Unpooled
                .buffer(2048, 2048)
                .writeInt(PacketType.PACKET_SCENE_STATE.ordinal())
                .writeLong(System.currentTimeMillis());
    }
}
