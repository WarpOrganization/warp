package pl.warp.engine.core.context.task;

import pl.warp.engine.core.context.ServiceRegistry;
import pl.warp.engine.core.context.executor.ExecutorManager;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.EngineThread;
import pl.warp.engine.core.execution.Executor;
import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */

@Service
public class TaskManager implements ServiceRegistry {

    private ExecutorManager executorManager;

    public TaskManager(ExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    @Override
    public void registerService(Object service) {
        RegisterTask annotation = service.getClass().getAnnotation(RegisterTask.class);
        if (annotation != null)
            registerTask(service, annotation);
    }

    private void registerTask(Object service, RegisterTask annotation) {
        Executor executor = executorManager.getExecutor(annotation.thread());
        if (!EngineThread.class.isAssignableFrom(executor.getClass()))
            throw new TaskLoadingException(annotation.thread() + " is not the EngineThread", service);
        if (!EngineTask.class.isAssignableFrom(service.getClass()))
            throw new TaskLoadingException("Service is not the EngineTask", service);
        EngineThread thread = (EngineThread) executor;
        thread.scheduleTask((EngineTask) service);
    }


    @Override
    public int getPriority() {
        return 2;
    }
}
