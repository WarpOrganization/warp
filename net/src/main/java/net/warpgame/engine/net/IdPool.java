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
        if (poolState == IdPoolState.AWAITING) poolState = IdPoolState.ACTIVE;
        isTaken[nextId] = true;
        nIdsTaken++;
        nextId++;
        if (nextId == ID_POOL_SIZE) poolState = IdPoolState.FREEING;
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
        if (poolState == IdPoolState.FREEING) {
            if (nIdsTaken == 0) {
                nextId = 0;
                poolState = IdPoolState.AWAITING;
            }
        }
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

    public void setPoolState(IdPoolState poolState) {
        this.poolState = poolState;
    }

    public enum IdPoolState {
        AWAITING,
        ACTIVE,
        FREEING
    }
}
