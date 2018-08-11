package net.warpgame.engine.net.message;

import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Service;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
@Service
public class MessageSourcesService {

    private MessageSource[] messageSources;

    public MessageSourcesService(Context context,
                                 MessageSource[] messageSources) {
        this.messageSources = messageSources;
    }

    public void update() {
        for (MessageSource messageProcessor : messageSources) messageProcessor.processMessages();
    }
}
