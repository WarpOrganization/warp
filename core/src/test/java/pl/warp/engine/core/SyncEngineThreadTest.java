package pl.warp.engine.core;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 13
 */
public class SyncEngineThreadTest {
    @Test
    public void shouldInitScheduledTask() throws Exception {
        Timer timer = mock(Timer.class);
        when(timer.getDelta()).thenReturn(0);
        doNothing().when(timer).await();
        ScheduledExecutionStrategy strategy = mock(ScheduledExecutionStrategy.class);
        doNothing().when(strategy).execute(any());

        SyncEngineThread engineThread = new SyncEngineThread(timer, strategy);
        EngineTask task = mock(EngineTask.class);
        engineThread.scheduleTask(task);
        engineThread.start();
        Thread.sleep(50);
        verify(task).init();
    }

    @Test
    public void shouldRunScheduledTask() throws Exception {
        Timer timer = mock(Timer.class);
        when(timer.getDelta()).thenReturn(42);
        doNothing().when(timer).await();
        ScheduledExecutionStrategy strategy = mock(ScheduledExecutionStrategy.class);
        doNothing().when(strategy).execute(any());

        SyncEngineThread engineThread = new SyncEngineThread(timer, strategy);
        EngineTask task = mock(EngineTask.class);
        engineThread.scheduleTask(task);
        engineThread.runUpdate();
        verify(task, atLeast(1)).update(eq(42));
    }

    @Test
    public void shouldRunScheduledRunnables() throws Exception {
        Timer timer = mock(Timer.class);
        when(timer.getDelta()).thenReturn(42);
        doNothing().when(timer).await();
        ScheduledExecutionStrategy strategy = new RapidExecutionStrategy();

        SyncEngineThread engineThread = new SyncEngineThread(timer, strategy);
        Runnable runnable = mock(Runnable.class);
        engineThread.scheduleOnce(runnable);
        engineThread.runUpdate();
        verify(runnable).run();
    }

    @Test
    public void shouldRunUpdateWhenRunning() throws Exception {
        Timer timer = mock(Timer.class);
        when(timer.getDelta()).thenReturn(0);
        doNothing().when(timer).await();
        ScheduledExecutionStrategy strategy = mock(ScheduledExecutionStrategy.class);
        doNothing().when(strategy).execute(any());

        AtomicBoolean flag = new AtomicBoolean();
        SyncEngineThread engineThread = new SyncEngineThread(timer, strategy) {
            @Override
            protected void runUpdate() {
                flag.set(true);
            }
        };
        EngineTask task = mock(EngineTask.class);
        engineThread.scheduleTask(task);
        engineThread.start();
        Thread.sleep(100);
        assertTrue(flag.get());
    }


}