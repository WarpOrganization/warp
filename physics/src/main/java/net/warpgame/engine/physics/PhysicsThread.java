package net.warpgame.engine.physics;

import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

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
