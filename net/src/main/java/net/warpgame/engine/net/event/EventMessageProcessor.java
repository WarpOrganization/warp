package net.warpgame.engine.net.event;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.net.ConnectionTools;
import net.warpgame.engine.net.DesynchronizationException;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.BasicMessageDeserializer;
import net.warpgame.engine.net.message.MessageProcessor;
import net.warpgame.engine.core.serialization.Serializers;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class EventMessageProcessor implements MessageProcessor {

    private BasicMessageDeserializer basicMessageDeserializer;
    private ComponentRegistry componentRegistry;
    private ConnectionTools connectionTools;
    private Serializers serializers;

    public EventMessageProcessor(ComponentRegistry componentRegistry,
                                 ConnectionTools connectionTools,
                                 Serializers serializers) {
        this.connectionTools = connectionTools;
        this.serializers = serializers;
        this.basicMessageDeserializer = new BasicMessageDeserializer();
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void processMessage(Peer sourcePeer, ByteBuf messageContent) {
        byte[] bytes = new byte[messageContent.readableBytes()];
        messageContent.readBytes(bytes);
        SerializationBuffer buffer = new SerializationBuffer(bytes);

        int eventId = buffer.readInt();
        int targetComponentId = buffer.readInt();
        Component targetComponent = componentRegistry.getComponent(targetComponentId);
        if (targetComponent == null) throw new DesynchronizationException("Target component not found");

        NetworkEvent networkEvent = (NetworkEvent) serializers.deserialize(buffer);
        networkEvent.setTransfered(true);
        networkEvent.setSourceId(sourcePeer.getId());
        networkEvent.setTargetId(connectionTools.getPeerId());

        targetComponent.triggerEvent(networkEvent);
    }

    @Override
    public int getMessageType() {
        //TODO implement runtime messageType generation
        return 0;
    }
}
