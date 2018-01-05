package net.warpgame.engine.server;

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
@RegisterExecutor("server")
public class ServerThread extends SyncEngineThread {
    public ServerThread() {
        super(new SyncTimer(60), new RapidExecutionStrategy());
    }

}
