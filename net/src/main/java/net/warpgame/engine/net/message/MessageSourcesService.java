package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class MessageSourcesService {
    private List<MessageSource> messageSources;

    public MessageSourcesService(Context context,
                                 EventMessageSource eventMessageSource,
                                 InternalMessageSource internalMessageSource) {
        messageSources = new ArrayList<>();
        messageSources.add(eventMessageSource);
        messageSources.add(internalMessageSource);
    }

    public void update() {
        for (MessageSource messageProcessor : messageSources) messageProcessor.processMessages();
    }
}
