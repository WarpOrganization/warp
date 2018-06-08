package net.warpgame.engine.core.script.annotation;

import net.warpgame.engine.core.component.IdOf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 12
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OwnerProperty {
    IdOf value();
}
