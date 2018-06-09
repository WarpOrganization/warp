package net.warpgame.engine.physics;

/**
 * @author Hubertus
 * Created 07.10.2017
 */
public class IdDispatcher {
    private int currentId = 0;

    public synchronized int getNextID() {
        currentId++;
        return currentId;
    }
}
