package pl.warp.engine.core.script;

import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.*;

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
