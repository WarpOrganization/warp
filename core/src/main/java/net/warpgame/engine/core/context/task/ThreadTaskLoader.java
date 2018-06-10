package net.warpgame.engine.core.context.task;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.execution.task.EngineTask;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jaca777
 * Created 2018-01-05 at 23
 */
public class ThreadTaskLoader {

    private EngineThread thread;
    private Set<EngineTask> tasks = new HashSet<>();
    private Map<Class<? extends EngineTask>, EngineTask> tasksByClasses = new HashMap<>();
    private Multimap<Class<? extends EngineTask>, Class<? extends EngineTask>> taskDependencies = HashMultimap.create();

    public ThreadTaskLoader(EngineThread thread) {
        this.thread = thread;
    }

    public void addTask(EngineTask task) {
        tasks.add(task);
        tasksByClasses.put(task.getClass(), task);
    }

    public void addTaskDependency(Class<? extends EngineTask> from, Class<? extends EngineTask> to) {
        taskDependencies.put(from, to);
    }

    public void addTasksToThread() {
        Map<EngineTask, List<EngineTask>> taskDependencies = getTasksExecutionDependencies();
        List<EngineTask> values = toDependencies(taskDependencies);
        List<EngineTask> orderedTasks = getDependencyGraphRoots(values);

        while (!taskDependencies.isEmpty())
            findRootDependenciesAndInsert(taskDependencies, orderedTasks);

        orderedTasks.forEach(task -> thread.scheduleTask(task));
    }

    private void findRootDependenciesAndInsert(
            Map<EngineTask, List<EngineTask>> taskDependencies,
            List<EngineTask> orderedTasks
    ) {
        Map<EngineTask, List<EngineTask>> dependenciesCopy = new HashMap<>(taskDependencies);
        Stream<Map.Entry<EngineTask, List<EngineTask>>> currentDepthTasks = dependenciesCopy.entrySet().stream()
                .filter(e -> orderedTasks.contains(e.getKey()));
        insertRootDependencies(taskDependencies, orderedTasks, currentDepthTasks);
    }

    private void insertRootDependencies(
            Map<EngineTask, List<EngineTask>> taskDependencies,
            List<EngineTask> orderedTasks,
            Stream<Map.Entry<EngineTask, List<EngineTask>>> currentDepthTasks
    ) {
        currentDepthTasks
                .flatMap(e -> e.getValue().stream().map(s -> ImmutablePair.of(e.getKey(), s)))
                .forEach(dependency -> insertTaskOrdered(taskDependencies, orderedTasks, dependency));
    }

    private void insertTaskOrdered(
            Map<EngineTask, List<EngineTask>> taskDependencies,
            List<EngineTask> orderedTasks,
            ImmutablePair<EngineTask, EngineTask> t
    ) {
        int dependencyIndex = orderedTasks.indexOf(t.getLeft());
        orderedTasks.add(dependencyIndex, t.getRight());
        taskDependencies.remove(t.getLeft());
    }

    private List<EngineTask> getDependencyGraphRoots(List<EngineTask> values) {
        return tasks.stream()
                .filter(s -> !values.contains(s))
                .collect(Collectors.toList());
    }

    private List<EngineTask> toDependencies(Map<EngineTask, List<EngineTask>> taskDependencies) {
        return taskDependencies.entrySet()
                .stream()
                .flatMap(s -> s.getValue().stream())
                .collect(Collectors.toList());
    }

    private Map<EngineTask, List<EngineTask>> getTasksExecutionDependencies() {
        return taskDependencies.asMap().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> tasksByClasses.get(e.getKey()),
                        e -> e.getValue().stream().map(s -> tasksByClasses.get(s)).collect(Collectors.toList())
                ));
    }
}
