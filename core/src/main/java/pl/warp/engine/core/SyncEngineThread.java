package pl.warp.engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public class SyncEngineThread implements EngineThread {

    private List<EngineTask> tasks = new ArrayList<>();
    private Queue<Runnable> scheduledRunnables = new ConcurrentLinkedQueue<>();
    private Timer timer;
    private ScheduledExecutionStrategy executionStrategy;
    private boolean running;
    private Thread engineThread;

    public SyncEngineThread(Timer timer, ScheduledExecutionStrategy executionStrategy) {
        this.timer = timer;
        this.executionStrategy = executionStrategy;
    }

    @Override
    public void scheduleOnce(Runnable runnable) {
        scheduledRunnables.add(runnable);
    }

    @Override
    public void scheduleTask(EngineTask task) {
        if (running) scheduleOnce(() -> tasks.add(task));
        else tasks.add(task);
    }

    @Override
    public void start() {
        engineThread = new Thread(this::startEngine);
        engineThread.start();
    }

    private void startEngine() {
        running = true;
        loop();
        close();
    }


    private void loop() {
        while (running) runUpdate();
    }

    protected void runUpdate() {
        int delta = timer.getDelta();
        for (EngineTask task : tasks) {
            if (!task.isInitialized()) task.init();
            task.update(delta);
        }
        executionStrategy.execute(scheduledRunnables);
        timer.await();
    }

    private void close() {
        tasks.forEach(EngineTask::close);
    }

    @Override
    public void interrupt() {
        if (!running) throw new ThreadNotRunningException();
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
