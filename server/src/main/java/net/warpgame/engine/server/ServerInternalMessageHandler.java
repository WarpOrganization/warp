package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.event.InternalMessageEnvelope;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
@Service
public class ServerInternalMessageHandler implements net.warpgame.engine.net.event.InternalMessageHandler {
    @Override
    public void handleMessage(InternalMessageEnvelope message) {

    }
}
