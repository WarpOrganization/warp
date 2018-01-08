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

        Map<EngineTask, List<EngineTask>> instanceDependencies = taskDependencies.asMap().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> tasksByClasses.get(e.getKey()),
                        e -> e.getValue().stream().map(s -> tasksByClasses.get(s)).collect(Collectors.toList())
                ));

        List<EngineTask> values = instanceDependencies.entrySet()
                .stream()
                .flatMap(s -> s.getValue().stream())
                .collect(Collectors.toList());

        List<EngineTask> orderedTasks = tasks.stream()
                .filter(s -> !values.contains(s))
                .collect(Collectors.toList());

        while (!instanceDependencies.isEmpty()) {

            Map<EngineTask, List<EngineTask>> dependenciesCopy = instanceDependencies.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Stream<Map.Entry<EngineTask, List<EngineTask>>> currentDepthTasks = dependenciesCopy.entrySet().stream()
                    .filter(e -> orderedTasks.contains(e.getKey()));

            currentDepthTasks
                    .flatMap(e -> e.getValue().stream().map(s -> ImmutablePair.of(e.getKey(), s)))
                    .forEach(t -> {
                        int dependencyIndex = orderedTasks.indexOf(t.getLeft());
                        orderedTasks.add(dependencyIndex, t.getRight());
                        instanceDependencies.remove(t.getLeft());
                    });
        }

        orderedTasks.forEach(task -> thread.scheduleTask(task));
    }
}
