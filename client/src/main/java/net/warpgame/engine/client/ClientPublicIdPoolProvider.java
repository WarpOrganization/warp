package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.IdPool;
import net.warpgame.engine.net.PublicIdPoolProvider;

import java.util.PriorityQueue;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
@Service
@Profile("client")
public class ClientPublicIdPoolProvider extends PublicIdPoolProvider {

    private PriorityQueue<IdPool> availablePools = new PriorityQueue<>();

    @Override
    public IdPool requestIdPool() {
        IdPool pool;
        if (!availablePools.isEmpty()) {
            pool = availablePools.poll();
            if (availablePools.isEmpty()) requestNewIdPool();
        } else throw new OutOfIdPoolsException();
        return pool;
    }

    private void requestNewIdPool() {

    }

    @Override
    public void freeIdPool(IdPool idPool) {
        availablePools.add(idPool);
    }

    public void offerIssuedIdPool(IdPool idPool) {
        //TODO implement
    }
}
