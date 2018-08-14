package net.warpgame.engine.net;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
public abstract class PublicIdPoolProvider {
    protected PriorityQueue<IdPool> availableIdPools = new PriorityQueue<>();
    protected Map<Integer, IdPool> freeingIdPools = new HashMap<>();

    public abstract IdPool requestIdPool();


    protected void checkForFreedIdPools() {
        for (IdPool idPool : freeingIdPools.values()) {
            if (idPool.getPoolState() == IdPool.IdPoolState.AWAITING) {
                freeingIdPools.remove(idPool);
                availableIdPools.add(idPool);
            }
        }
    }

    //TODO test or something
    public IdPool getPoolByComponentId(int id) {
        int offset;
        if (id < 0) {
            if (id % IdPool.ID_POOL_SIZE == 0) offset = id / IdPool.ID_POOL_SIZE * IdPool.ID_POOL_SIZE;
            else offset = ((id / IdPool.ID_POOL_SIZE) - 1) * IdPool.ID_POOL_SIZE;
        } else {
            offset = id / IdPool.ID_POOL_SIZE * IdPool.ID_POOL_SIZE;
        }
        return freeingIdPools.get(offset);
    }
}
