package net.warpgame.engine.net;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
public abstract class PublicIdPoolProvider {
    protected PriorityQueue<IdPool> availableIdPools = new PriorityQueue<>();
    private ArrayList<IdPool> freeingIdPools = new ArrayList<>();

    public abstract IdPool requestIdPool();

    public synchronized void freeIdPool(IdPool idPool) {
        freeingIdPools.add(idPool);
    }

    protected void checkForFreedIdPools() {
        for (IdPool idPool : freeingIdPools) {
            if (idPool.getPoolState() == IdPool.IdPoolState.AWAITING) {
                freeingIdPools.remove(idPool);
                availableIdPools.add(idPool);
            }
        }
    }
}
