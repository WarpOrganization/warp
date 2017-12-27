package pl.warp.engine.core.runtime.processing;

/**
 * @author Jaca777
 * Created 2017-12-20 at 21
 */
public class ComposedProcessor<T> implements Processor<T> {

    private Processor<T>[] processors;

    @SafeVarargs
    public ComposedProcessor(Processor<T>... processors) {
        this.processors = processors;
    }

    @Override
    public void initializeProcessing() {
        for (Processor<T> processor : processors) {
            processor.initializeProcessing();
        }
    }

    @Override
    public void process(T t) {
        for (Processor<T> processor : processors) {
            processor.process(t);
        }
    }

    @Override
    public void finalizeProcessing() {
        for (Processor<T> processor : processors) {
            processor.finalizeProcessing();
        }
    }
}
