package net.warpgame.engine.core.context.task;

import net.warpgame.engine.core.execution.task.EngineTask;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jaca777
 * Created 2017-12-16 at 22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExecuteBeforeTask {
    Class<? extends EngineTask> value();
}
