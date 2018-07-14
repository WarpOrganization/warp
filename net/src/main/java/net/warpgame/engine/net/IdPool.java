package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 12.07.2018
 */
public class IdPool {
    private int size;
    private int offset;
    private boolean[] isTaken;
    int nextId = 0;
    int nComponentsAlive;
    private IdPoolState poolState = IdPoolState.AWAITING;


//    public int getNextId() {
//
//    }

    public void freeId(int id) {

    }

    private enum IdPoolState {
        AWAITING,
        ACTIVE,
        FREEING
    }
}
