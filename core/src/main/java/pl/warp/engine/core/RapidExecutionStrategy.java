package pl.warp.engine.core;

import java.util.Queue;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public class RapidExecutionStrategy implements ScheduledExecutionStrategy {
    @Override
    public void execute(Queue<Runnable> runnables) {
        runnables.forEach(Runnable::run);
    }
}
