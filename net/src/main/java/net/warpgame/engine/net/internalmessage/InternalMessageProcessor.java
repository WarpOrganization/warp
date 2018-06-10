package net.warpgame.engine.net.internalmessage;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;
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

    public InternalMessageProcessor(InternalMessageHandler internalMessageHandler) {
        this.internalMessageHandler = internalMessageHandler;
        messageDeserializer = new BasicMessageDeserializer();
    }

    @Override
    public void processMessage(Peer sourcePeer, ByteBuf messageContent) {
        InternalMessage message = (InternalMessage) messageDeserializer.deserialize(messageContent);
        internalMessageHandler.handleMessage(message, sourcePeer);
    }

    @Override
    public int getMessageType() {
        return 1;
    }
}
