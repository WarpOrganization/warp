package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
@Profile("net")
public class MessageProcessorsService {

    private HashMap<Integer, MessageProcessor> messageProcessorsMap;

    public MessageProcessorsService(MessageProcessor[] messageProcessors) {
        messageProcessorsMap = new HashMap<>();
        for(MessageProcessor messageProcessor : messageProcessors)
            messageProcessorsMap.put(messageProcessor.getMessageType(), messageProcessor);
    }

    public MessageProcessor getMessageProcessor(int messageType) {
        return messageProcessorsMap.get(messageType);
    }
}
