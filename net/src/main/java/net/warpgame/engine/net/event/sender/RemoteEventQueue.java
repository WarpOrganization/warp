package net.warpgame.engine.net.event.sender;

import net.warpgame.engine.net.event.Envelope;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public interface RemoteEventQueue {
    void pushEvent(Envelope event);
    void update();
}
