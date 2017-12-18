package pl.warp.engine.core.runtime.processing;


import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2017-12-18 at 22
 */
public class ComposedSteppingProcessor implements Processor {

    private int currentStep = 0;
    private Processor[] processors;

    public ComposedSteppingProcessor(Processor... processors) {
        this.processors = processors;
    }

    @Override
    public void process(ClassNode classNode) {
        if (currentStep > processors.length)
            throw new IllegalStateException("Processor doesn't contain processor with step index " + currentStep);
        else {
            Processor currentProcessor = processors[currentStep];
            currentProcessor.process(classNode);
        }
    }

    @Override
    public void finalizeProcessing() {
        if (currentStep > processors.length)
            throw new IllegalStateException("Processor doesn't contain processor with step index " + currentStep);
        else {
            Processor currentProcessor = processors[currentStep];
            currentProcessor.finalizeProcessing();
            currentStep++;
        }
    }

    public boolean hasNextStep() {
        return currentStep < processors.length - 1;
    }

}
