package net.warpgame.engine.graphics;


import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
@Service
@Profile("graphics")
@EnableConfig
@RegisterExecutor("graphics")
public class GraphicsThread extends SyncEngineThread {
    public GraphicsThread(Config config) {
        super(new SyncTimer(config.getValue("graphics.ups")), new RapidExecutionStrategy());
    }
}
