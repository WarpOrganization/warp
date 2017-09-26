package pl.warp.engine.graphics;


import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.config.EnableConfig;
import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.RapidExecutionStrategy;
import pl.warp.engine.core.execution.SyncEngineThread;
import pl.warp.engine.core.execution.SyncTimer;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
@Service
@EnableConfig
@RegisterExecutor("graphics")
public class GraphicsThread extends SyncEngineThread {
    public GraphicsThread(Config config) {
        super(new SyncTimer(config.getValue("graphics.ups")), new RapidExecutionStrategy());
    }
}
