package pl.warp.engine.game.script;

import pl.warp.engine.core.script.Script;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.updatescheduler.DelaySchedulerImpl;
import pl.warp.engine.game.script.updatescheduler.TickIntervalSchedulerImpl;
import pl.warp.engine.game.script.updatescheduler.UpdateScheduler;

/**
 * @author Jaca777
 *         Created 2017-01-27 at 17
 */

public abstract class GameScript<T extends GameComponent> extends Script<T> {
    private GameContext gameContext;
    private UpdateScheduler scheduler;

    public GameScript(T owner) {
        super(owner);
        this.gameContext = owner.getContext();
    }

    @Override
    protected GameContext getContext() {
        return gameContext;
    }


    @Override
    public void onInit() {
        init();
    }


    protected abstract void init();

    @Override
    public void onUpdate(int delta) {
        scheduler.update(delta);
        while (scheduler.pollUpdate())
            update(delta);
    }

    protected abstract void update(int delta);

    protected void setScheduler(UpdateScheduler scheduler) {
        this.scheduler = scheduler;
    }

    protected void scheduleByTickIntervals(int ticks){
        if(scheduler instanceof TickIntervalSchedulerImpl)
            ((TickIntervalSchedulerImpl) scheduler).setIntervalTicks(ticks);
        else setScheduler(new TickIntervalSchedulerImpl(ticks));
    }

    protected void scheduleByDelay(int delayInMillis){
        if(scheduler instanceof DelaySchedulerImpl)
            ((DelaySchedulerImpl) scheduler).setDelay(delayInMillis);
        else setScheduler(new DelaySchedulerImpl(delayInMillis));
    }


    protected void log(String info){
        System.out.println(info);
    }
}
