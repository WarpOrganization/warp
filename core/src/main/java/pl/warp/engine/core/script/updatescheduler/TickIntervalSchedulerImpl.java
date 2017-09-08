package pl.warp.engine.core.script.updatescheduler;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public class TickIntervalSchedulerImpl implements UpdateScheduler {

    private int intervalTicks;
    private int ticksUntilUpdate;

    public TickIntervalSchedulerImpl(int intervalTicks) {
        if(intervalTicks <= 0)
            throw new IllegalArgumentException("Number of ticks has to be positive.");
        this.intervalTicks = intervalTicks;
        this.ticksUntilUpdate = intervalTicks;
    }


    @Override
    public int pollUpdates(int delta) {
        this.ticksUntilUpdate--;
        if(ticksUntilUpdate == 0){
            ticksUntilUpdate = intervalTicks;
            return 1;
        } else return 0;
    }

    public void setIntervalTicks(int intervalTicks) {
        this.intervalTicks = intervalTicks;
    }

    public void setTicksUntilUpdate(int ticksUntilUpdate) {
        this.ticksUntilUpdate = ticksUntilUpdate;
    }
}

