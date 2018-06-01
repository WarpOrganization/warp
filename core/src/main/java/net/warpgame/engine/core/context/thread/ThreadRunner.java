package net.warpgame.engine.core.context.thread;

import net.warpgame.engine.core.context.ServiceRegistry;
import net.warpgame.engine.core.context.executor.RegisterExecutor;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.EngineThread;

/**
 * @author Jaca777
 * Created 2017-09-23 at 20
 */
@Service
public class ThreadRunner implements ServiceRegistry {

    @Override
    public void registerService(Object service) {
       if(isRunnableThread(service))
           ((EngineThread) service).start();
    }

    private boolean isRunnableThread(Object service) {
        Class<?> serviceClass = service.getClass();
        return EngineThread.class.isAssignableFrom(serviceClass)
                && (serviceClass.getAnnotation(RegisterExecutor.class) != null);
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
