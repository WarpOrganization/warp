package net.warpgame.engine.net.event;

/**
 * @author Hubertus
 * Created 14.05.2018
 */
public interface StateChangeHandler {
    void handleMessage(StateChangeRequestMessage message);
}
