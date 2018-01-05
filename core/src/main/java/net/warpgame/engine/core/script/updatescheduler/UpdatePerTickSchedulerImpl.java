package net.warpgame.engine.core.script.updatescheduler;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public class UpdatePerTickSchedulerImpl implements UpdateScheduler {

    @Override
    public int pollUpdates(int delta) {
        return 1;
    }
}
