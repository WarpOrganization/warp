package net.warpgame.engine.core.context.task;

import net.warpgame.engine.core.context.ServiceRegistry;
import net.warpgame.engine.core.context.executor.ExecutorManager;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.execution.Executor;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */

@Service
public class TaskManager implements ServiceRegistry {

    private ExecutorManager executorManager;
    private Map<EngineThread, ThreadTaskLoader> loaders = new HashMap<>();

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
        addTask((EngineTask) service, thread);
    }

    private void addTask(EngineTask task, EngineThread thread) {
        if (!loaders.containsKey(thread))
            loaders.put(thread, new ThreadTaskLoader(thread));
        ThreadTaskLoader taskLoader = loaders.get(thread);
        taskLoader.addTask(task);
        processDependencies(taskLoader, task);
    }

    private void processDependencies(ThreadTaskLoader taskLoader, EngineTask task) {
        Class<? extends EngineTask> aClass = task.getClass();

        if (aClass.getAnnotation(InsertAfterTask.class) != null) {
            InsertAfterTask annotation = aClass.getAnnotation(InsertAfterTask.class);
            Class<? extends EngineTask> value = annotation.value();
            taskLoader.addTaskDependency(value, task.getClass());
        } else if (aClass.getAnnotation(InsertBeforeTask.class) != null) {
            InsertBeforeTask annotation = aClass.getAnnotation(InsertBeforeTask.class);
            Class<? extends EngineTask> value = annotation.value();
            taskLoader.addTaskDependency(task.getClass(), value);
        }
    }


    @Override
    public void finalizeRegistration() {
        loaders.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(ThreadTaskLoader::addTasksToThread);
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
