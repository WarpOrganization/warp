package pl.warp.game.script.updatescheduler;

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
    public void update(int delta) {
        this.ticksUntilUpdate--;
    }

    @Override
    public boolean pollUpdate() {
        if(ticksUntilUpdate == 0){
            ticksUntilUpdate = intervalTicks;
            return true;
        } else return false;
    }
}
