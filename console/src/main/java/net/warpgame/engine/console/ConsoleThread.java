package net.warpgame.engine.console;

import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;

/**
 * @author KocproZ
 * Created 2018-01-09 at 21:25
 */
@Service
@RegisterExecutor("console")
public class ConsoleThread extends SyncEngineThread {

    public ConsoleThread() {
        super(new SyncTimer(10), new RapidExecutionStrategy());
    }
}
