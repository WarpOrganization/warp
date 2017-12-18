package pl.warp.engine.core.context.task;

import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 * Created 2017-12-16 at 22
 */
public @interface InsertBeforeTask {
    Class<? extends EngineTask> value();
}
