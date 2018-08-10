package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.IdPool;
import net.warpgame.engine.net.NetComponentRegistry;
import net.warpgame.engine.net.PublicIdPoolProvider;

import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
@Service
@Profile("server")
public class ServerPublicIdPoolProvider extends PublicIdPoolProvider {

    private PriorityQueue<IdPool> availablePools = new PriorityQueue<>();
    private int nextIdPoolOffset = NetComponentRegistry.PUBLIC_ID_POOL_BEGINNING;

    @Override
    public IdPool requestIdPool() {
        if(!availablePools.isEmpty()){
            return availablePools.poll();
        }else {
            IdPool newPool = new IdPool(nextIdPoolOffset);
            nextIdPoolOffset += IdPool.ID_POOL_SIZE;
            return newPool;
        }
    }

    @Override
    public void freeIdPool(IdPool idPool){
        availablePools.add(idPool);
    }

    public IdPool issueIdPool(Client client) {
        IdPool pool = requestIdPool();
        client.assignIdPool(pool);
        return pool;
    }
}
