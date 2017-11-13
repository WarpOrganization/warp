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

    /**
     * Execution loop iteration is guaranteed to have these ordered steps:
     * 1. Initialization of uninitialized tasks
     * 2. Execution of scheduled runnables using selected strategy
     * 3. Task updates
     */
    void start();
    void interrupt();
    boolean isRunning();
}
