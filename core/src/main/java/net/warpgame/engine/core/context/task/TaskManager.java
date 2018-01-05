package net.warpgame.engine.core.context.task;

import net.warpgame.engine.core.context.executor.ExecutorManager;
import net.warpgame.engine.core.context.ServiceRegistry;
import net.warpgame.engine.core.context.executor.ExecutorManager;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.execution.Executor;
import net.warpgame.engine.core.execution.task.EngineTask;

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
