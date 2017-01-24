package pl.warp.engine.core;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public interface EngineThread {
    void scheduleOnce(Runnable runnable);

    void scheduleTask(EngineTask task);
    void start();
    void interrupt();
    boolean isRunning();
}
