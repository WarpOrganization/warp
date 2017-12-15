package pl.warp.engine.server;

import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.RapidExecutionStrategy;
import pl.warp.engine.core.execution.SyncEngineThread;
import pl.warp.engine.core.execution.SyncTimer;

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
