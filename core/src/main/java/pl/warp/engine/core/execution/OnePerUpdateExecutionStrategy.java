package pl.warp.engine.core.execution;

import java.util.Queue;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 21
 */
public class OnePerUpdateExecutionStrategy implements ScheduledExecutionStrategy {
    @Override
    public void execute(Queue<Runnable> runnables) {
        if(!runnables.isEmpty()) runnables.remove().run();
    }
}
