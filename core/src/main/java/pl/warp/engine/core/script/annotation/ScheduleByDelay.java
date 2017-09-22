package pl.warp.engine.core.script.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScheduleByDelay {
    int delayInMillis();
}
