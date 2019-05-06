package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.graphics.VulkanTask;

import java.util.stream.Stream;

/**
 * @author MarconZet
 * Created 06.05.2019
 */
public class GraphicsTest {

    private static EngineContext context;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        context = new EngineContext("dev", "graphics", "local");
        context.getLoadedContext().addService(engineRuntime.getIdRegistry());
        VulkanTask vulkanTask = context.getLoadedContext().findOne(VulkanTask.class).get();
        while(!vulkanTask.isInitialized()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    private static void close() {
        context.getLoadedContext().findAll(EngineThread.class).forEach(EngineThread::interrupt);
        Stream<EngineTask> engineTasks = context.getLoadedContext().findAll(EngineTask.class).stream();
        while(!engineTasks.allMatch(EngineTask::isInitialized)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
