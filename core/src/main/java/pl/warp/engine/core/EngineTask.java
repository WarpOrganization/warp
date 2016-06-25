package pl.warp.engine.core;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public abstract class EngineTask {
    private boolean initialized;

    void callInit() {
        if (initialized) throw new TaskInitializedException();
        init();
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public abstract void init();

    public abstract void update(long delta);
}
