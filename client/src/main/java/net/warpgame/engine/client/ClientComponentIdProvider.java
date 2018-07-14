package net.warpgame.engine.client;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.IdPool;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * @author Hubertus
 * Created 13.07.2018
 */
@Service
@Profile("client")
public class ClientComponentIdProvider {
    private ArrayDeque<IdPool> awaitingPublicIdPools = new ArrayDeque<>();
    private ArrayDeque<IdPool> awaitingPrivatePools = new ArrayDeque<>();
    private IdPool currentPublicPool;
    private IdPool currentPrivatePool;
    private HashMap<Integer, IdPool> idPools = new HashMap<>();

    private static final int MINIMAL_PUBLIC_POOL_CAPACITY = 5000;

    public ClientComponentIdProvider() {
    }

    public void receivePublicPool() {

    }

    public void registerComponent() {

    }

    private void freeComponent(Component c) {
        if (isFromPrivatePool(c.getId())) {

        } else {
            IdPool idPool = idPools.get(getPoolId(c.getId()));
            if (idPool != null) idPool.freeId(c.getId());
        }
    }

    private void requestPublicPool() {

    }

    private boolean isFromPrivatePool(int componentId) {
        return componentId < Integer.MIN_VALUE + 100000;
    }

    private int getPoolId(int componentId) {
        return componentId - (componentId % 5000);
    }

    private void handlePublicPoolFreed(){

    }
}
