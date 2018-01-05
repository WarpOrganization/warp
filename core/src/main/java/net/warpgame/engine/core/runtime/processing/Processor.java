package net.warpgame.engine.core.runtime.processing;


/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public interface Processor <T> {
    default void initializeProcessing() {}
    void process(T t);
    default void finalizeProcessing() {}
}
