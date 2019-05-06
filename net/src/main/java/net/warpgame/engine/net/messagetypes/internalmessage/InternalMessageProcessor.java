package net.warpgame.engine.net.messagetypes.internalmessage;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import net.warpgame.engine.net.Peer;
import net.warpgame.engine.net.message.BasicMessageDeserializer;
import net.warpgame.engine.net.message.MessageProcessor;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
@Profile("net")
public class InternalMessageProcessor implements MessageProcessor {

    private BasicMessageDeserializer messageDeserializer;
    private InternalMessageHandler internalMessageHandler;
    private Serializers serializers;

    public InternalMessageProcessor(InternalMessageHandler internalMessageHandler, Serializers serializers) {
        this.internalMessageHandler = internalMessageHandler;
        this.serializers = serializers;
        messageDeserializer = new BasicMessageDeserializer();
    }

    @Override
    public void processMessage(Peer sourcePeer, SerializationBuffer messageContent) {
        InternalMessage message = (InternalMessage) serializers.deserialize(messageContent);
        internalMessageHandler.handleMessage(message, sourcePeer);
    }

    @Override
    public int getMessageType() {
        //TODO implement runtime messageType generation
        return 1;
    }
}
