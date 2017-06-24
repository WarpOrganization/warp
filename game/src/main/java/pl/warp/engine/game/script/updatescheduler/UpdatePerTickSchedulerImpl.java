package pl.warp.engine.game.script.updatescheduler;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public class UpdatePerTickSchedulerImpl implements UpdateScheduler {

    private boolean updated = false;

    @Override
    public void update(int delta) {
        updated = true;
    }

    @Override
    public boolean pollUpdate() {
        if (updated) {
            updated = false;
            return true;
        } else return false;
    }
}
