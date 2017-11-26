package pl.warp.engine.physics;

import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.RapidExecutionStrategy;
import pl.warp.engine.core.execution.SyncEngineThread;
import pl.warp.engine.core.execution.SyncTimer;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
@Service
@RegisterExecutor("physics")
public class PhysicsThread extends SyncEngineThread {

    public PhysicsThread() {
        super(new SyncTimer(60), new RapidExecutionStrategy());
    }
}
