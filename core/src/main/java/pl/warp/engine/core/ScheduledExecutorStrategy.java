package pl.warp.engine.core;

import java.util.Queue;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public interface ScheduledExecutorStrategy {
    void execute(Queue<Runnable> runnables);
}
