package pl.warp.engine.core.updater;

import pl.warp.engine.core.SimpleEngineTask;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 22
 */
public class UpdaterTask  extends SimpleEngineTask {
    private Updatable updatable;

    public UpdaterTask(Updatable updatable) {
        this.updatable = updatable;
    }

    @Override
    public void update(int delta) {
        updatable.update(delta);
    }
}
