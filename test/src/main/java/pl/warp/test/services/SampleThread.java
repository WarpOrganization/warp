package pl.warp.test.services;

import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.*;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */

@Service
@RegisterExecutor("sample")
public class SampleThread extends SyncEngineThread{
    public SampleThread() {
        super(new SyncTimer(2), new RapidExecutionStrategy());
    }
}
