package pl.warp.engine.core.context.thread;

import pl.warp.engine.core.context.ServiceRegistry;
import pl.warp.engine.core.context.executor.RegisterExecutor;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.EngineThread;

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
