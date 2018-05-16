package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.InternalMessageEnvelope;
import net.warpgame.engine.net.event.InternalMessageHandler;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ClientInternalMessageHandler implements InternalMessageHandler {
    @Override
    public void handleMessage(InternalMessageEnvelope message) {
    }
}
