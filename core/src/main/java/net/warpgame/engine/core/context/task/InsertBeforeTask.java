package net.warpgame.engine.core.context.task;

import net.warpgame.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 * Created 2017-12-16 at 22
 */
public @interface InsertBeforeTask {
    Class<? extends EngineTask> value();
}
