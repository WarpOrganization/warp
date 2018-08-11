package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 12.07.2018
 */
//TODO write tests
public class IdPool implements Comparable<IdPool> {

    public static final int ID_POOL_SIZE = 5000;

    private int offset;
    private boolean[] isTaken = new boolean[ID_POOL_SIZE];
    private int nextId = 0;
    private int nIdsTaken = 0;
    private IdPoolState poolState = IdPoolState.AWAITING;


    public IdPool(int offset) {
        this.offset = offset;
    }

    public int getNextId() {
        isTaken[nextId] = true;
        nIdsTaken++;
        nextId++;
        stateCheck();
        return nextId - 1 + offset;
    }

    public void freeId(int id) {
        if (isTaken[id - offset]) {
            isTaken[id - offset] = false;
            nIdsTaken--;
            stateCheck();
        }
    }

    private void stateCheck() {
        if (nextId == ID_POOL_SIZE && nIdsTaken == 0) {
            nextId = 0;
            poolState = IdPoolState.AWAITING;
        } else if (poolState == IdPoolState.ACTIVE) poolState = IdPoolState.FREEING;
    }

    @Override
    public int compareTo(IdPool o) {
        return this.offset - o.offset;
    }

    public int getOffset() {
        return offset;
    }

    public IdPoolState getPoolState() {
        return poolState;
    }

    public enum IdPoolState {
        AWAITING,
        ACTIVE,
        FREEING
    }
}
