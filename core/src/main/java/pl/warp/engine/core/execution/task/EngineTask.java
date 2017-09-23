package pl.warp.engine.core.execution.task;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public abstract class EngineTask {
    private boolean initialized = false;

    /**
     * Call only this method in order to initialize the task.
     */
    public void init() {
        if (initialized)
            throw new TaskInitializedException();
        onInit();
        initialized = true;
    }

    public void close() {
        if(!initialized) throw new TaskNotInitializedException();
        onClose();
        initialized = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

    protected abstract void onInit();

    protected abstract void onClose();

    public abstract void update(int delta);

    public int getPriority() {
        return 0;
    }
}
