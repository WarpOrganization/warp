package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */

@FunctionalInterface
public interface Source<T> extends Named {
    T get(Context context) throws Exception;
}