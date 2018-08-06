package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */

@FunctionalInterface
public interface Processor<T, R> {
    R process(T t);
}
