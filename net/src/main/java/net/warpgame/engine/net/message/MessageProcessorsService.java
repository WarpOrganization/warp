package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.EventMessageProcessor;
import net.warpgame.engine.net.internalmessage.InternalMessageProcessor;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class MessageProcessorsService {

    private HashMap<Integer, MessageProcessor> messageProcessors;

    public MessageProcessorsService(Context context,
                                    EventMessageProcessor eventMessageProcessor,
                                    InternalMessageProcessor internalMessageProcessor) {
        messageProcessors = new HashMap<>();
        messageProcessors.put(eventMessageProcessor.getMessageType(), eventMessageProcessor);
        messageProcessors.put(internalMessageProcessor.getMessageType(), internalMessageProcessor);
        //TODO uncomment when findAll starts working
//        context
//                .findAll(MessageProcessor.class)
//                .forEach(
//                        messageProcessor ->
//                                messageProcessors.put(messageProcessor.getMessageType(), messageProcessor));
    }

    public MessageProcessor getMessageProcessor(int messageType) {
        return messageProcessors.get(messageType);
    }
}
