package pl.warp.engine.core.runtime.processing;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2017-12-20 at 21
 */
public class ComposedProcessor implements Processor {

    private Processor[] processors;

    public ComposedProcessor(Processor[] processors) {
        this.processors = processors;
    }

    @Override
    public void initializeProcessing() {
        for (Processor processor : processors) {
            processor.initializeProcessing();
        }
    }

    @Override
    public void process(ClassNode classNode) {
        for (Processor processor : processors) {
            processor.process(classNode);
        }
    }

    @Override
    public void finalizeProcessing() {
        for (Processor processor : processors) {
            processor.finalizeProcessing();
        }
    }
}
