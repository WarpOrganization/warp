package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.net.IdPool;
import net.warpgame.engine.net.PublicIdPoolProvider;
import net.warpgame.engine.net.message.IdPoolMessageSource;
import net.warpgame.engine.net.messagetypes.idpoolmessage.IdPoolRequest;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
@Service
@Profile("client")
public class ClientPublicIdPoolProvider extends PublicIdPoolProvider {

    private IdPoolMessageSource idPoolMessageSource;

    public ClientPublicIdPoolProvider(IdPoolMessageSource idPoolMessageSource) {
        this.idPoolMessageSource = idPoolMessageSource;
    }

    @Override
    public IdPool requestIdPool() {
        checkForFreedIdPools();
        if (!availableIdPools.isEmpty()) {
            IdPool pool = availableIdPools.poll();
            if (availableIdPools.isEmpty()) requestNewIdPool();
            return pool;
        } else throw new OutOfIdPoolsException();
    }

    private void requestNewIdPool() {
        idPoolMessageSource.pushMessage(new IdPoolRequest());
    }

    public void offerIssuedIdPool(int offset) {
        availableIdPools.add(new IdPool(offset));
    }

    public boolean hasPublicIdPoolReady() {
        return !availableIdPools.isEmpty();
    }
}
