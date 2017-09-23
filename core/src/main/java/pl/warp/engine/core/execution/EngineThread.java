package pl.warp.engine.core.execution;

import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public interface EngineThread extends Executor {
    @Override
    void scheduleOnce(Runnable runnable);
    void scheduleTask(EngineTask task);
    void start();
    void interrupt();
    boolean isRunning();
}
