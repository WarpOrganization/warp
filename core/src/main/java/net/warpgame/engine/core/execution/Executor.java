package net.warpgame.engine.core.execution;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */
public interface Executor {
    void scheduleOnce(Runnable runnable);
}
