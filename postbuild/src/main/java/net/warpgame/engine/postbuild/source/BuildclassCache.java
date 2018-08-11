package net.warpgame.engine.postbuild.source;

import org.objectweb.asm.tree.ClassNode;

import java.util.Optional;

/**
 * @author Jaca777
 * Created 2018-08-11 at 23
 */
public class BuildclassCache {
    public Optional<ClassNode> loadClassFromCache(String className) {
        return Optional.empty(); //todo
    }

    public void invalidateCache(String className) {
        //todo
    }
}
