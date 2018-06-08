package net.warpgame.engine.core.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jaca777
 * Created 2018-06-04 at 23
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface IdOf {
    Class<?> value();
    int id() default -1;
}
