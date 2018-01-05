package net.warpgame.engine.core.script.updatescheduler;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public class DelaySchedulerImpl implements UpdateScheduler {
    private int delay;
    private int delayAggregate;

    public DelaySchedulerImpl(int delay) {
        this.delay = delay;
    }

    @Override
    public int pollUpdates(int delta) {
        delayAggregate += delta;
        int updates = delayAggregate / delay;
        delayAggregate %= delay;
        return updates;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
