package net.warpgame.engine.net.messagetypes.internalmessage;

import net.warpgame.engine.net.Peer;

/**
 * @author Hubertus
 * Created 14.05.2018
 */
public interface InternalMessageHandler {
    void handleMessage(InternalMessage message, Peer sourcePeer);
}
