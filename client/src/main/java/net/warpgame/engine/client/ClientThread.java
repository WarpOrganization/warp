package net.warpgame.engine.client;

import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author Hubertus
 * Created 28.11.2017
 */
@Service
@RegisterExecutor("client")
public class ClientThread extends SyncEngineThread {

    public ClientThread() {
        super(new SyncTimer(60), new RapidExecutionStrategy());
    }
}
