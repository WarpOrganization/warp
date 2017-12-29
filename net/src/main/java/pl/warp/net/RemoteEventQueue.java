package pl.warp.net;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public interface RemoteEventQueue {
    void pushEvent(Envelope event);
    void update();
}
