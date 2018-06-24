package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class MessageProcessorsService {

    private HashMap<Integer, MessageProcessor> messageProcessors;

    public MessageProcessorsService(MessageProcessor[] processors) {
        messageProcessors = new HashMap<>();
        for(MessageProcessor processor : processors) {
            messageProcessors.put(processor.getMessageType(), processor);
        }
    }

    public MessageProcessor getMessageProcessor(int messageType) {
        return messageProcessors.get(messageType);
    }
}
