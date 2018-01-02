package pl.warp.net.event.sender;

import pl.warp.net.event.Envelope;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public interface RemoteEventQueue {
    void pushEvent(Envelope event);
    void update();
}
