package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-11 at 23
 */
public interface Named {
    default String getName() {
        return Integer.toHexString(hashCode());
    }
}
