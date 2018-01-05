package net.warpgame.engine.core.execution;

import java.util.Queue;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public interface ScheduledExecutionStrategy {
    void execute(Queue<Runnable> runnables);
}
