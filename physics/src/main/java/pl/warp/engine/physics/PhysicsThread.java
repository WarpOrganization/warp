package pl.warp.engine.physics;

import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.execution.*;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
@Service
public class PhysicsThread extends SyncEngineThread{

    public PhysicsThread() {
        super(new SyncTimer(60), new RapidExecutionStrategy());
    }
}
