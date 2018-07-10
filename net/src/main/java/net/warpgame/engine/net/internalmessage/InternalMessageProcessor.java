package net.warpgame.engine.net.internalmessage;

import io.netty.buffer.ByteBuf;
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
    public void processMessage(Peer sourcePeer, ByteBuf messageContent) {
        byte[] bytes = new byte[messageContent.readableBytes()];
        messageContent.readBytes(bytes);
        SerializationBuffer buffer = new SerializationBuffer(bytes);
        InternalMessage message = (InternalMessage) serializers.deserialize(buffer);
        internalMessageHandler.handleMessage(message, sourcePeer);
    }

    @Override
    public int getMessageType() {
        //TODO implement runtime messageType generation
        return 1;
    }
}
