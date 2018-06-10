package net.warpgame.engine.core.execution;

import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */
@RegisterExecutor("default")
@Service
//TODO merge with PoolEventDispatcher
public class DefaultCorePoolExecutor implements Executor {
    public static final int THREADS = Runtime.getRuntime().availableProcessors() * 4;
    private ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    @Override
    public void scheduleOnce(Runnable runnable) {
        executor.execute(runnable);
    }
}
