package net.warpgame.engine.core.context.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ConfigValue {
    String value();
    String dispatcher() default "default";
    boolean onlyOnChanges() default false;
}
