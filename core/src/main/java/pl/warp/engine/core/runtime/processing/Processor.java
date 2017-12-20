package pl.warp.engine.core.runtime.processing;


import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public interface Processor {
    default void initializeProcessing() {}
    void process(ClassNode classNode);
    default void finalizeProcessing() {};
}
