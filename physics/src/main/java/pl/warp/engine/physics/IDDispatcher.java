package pl.warp.engine.physics;

/**
 * @author Hubertus
 * Created 07.10.2017
 */
public class IDDispatcher {
    private int currentId = 0;

    public int getNextID() {
        currentId++;
        return currentId;
    }
}
