package net.warpgame.engine.net.event;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.DesynchronizationException;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.BasicMessageDeserializer;
import net.warpgame.engine.net.message.MessageProcessor;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class EventMessageProcessor implements MessageProcessor {

    private BasicMessageDeserializer basicMessageDeserializer;
    private ComponentRegistry componentRegistry;

    public EventMessageProcessor(ComponentRegistry componentRegistry) {
        this.basicMessageDeserializer = new BasicMessageDeserializer();
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void processMessage(Peer sourcePeer, ByteBuf messageContent) {
        //TODO implement fast deserialization
        int eventId = messageContent.readInt();
        int targetComponentId = messageContent.readInt();
        Component targetComponent = componentRegistry.getComponent(targetComponentId);
        if (targetComponent == null) throw new DesynchronizationException("Target component not found");
        NetworkEvent networkEvent = (NetworkEvent) basicMessageDeserializer.deserialize(messageContent);
        networkEvent.setTransfered(true);
        targetComponent.triggerEvent(networkEvent);
    }

    @Override
    public int getMessageType() {
        //TODO implement runtime messageType generation
        return 0;
    }
}
