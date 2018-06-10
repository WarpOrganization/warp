package net.warpgame.engine.net.event;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class ConnectedEvent extends NetworkEvent {
    public ConnectedEvent(int targetId) {
        super(targetId);
    }
}
