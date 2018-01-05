package net.warpgame.engine.core.script;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author Jaca777
 * Created 2017-09-23 at 23
 */

@Service
@RegisterExecutor("script")
public class ScriptThread extends SyncEngineThread {
    public ScriptThread(Config config) {
        super(new SyncTimer(config.getValue("script.ups")), new RapidExecutionStrategy());
    }
}
