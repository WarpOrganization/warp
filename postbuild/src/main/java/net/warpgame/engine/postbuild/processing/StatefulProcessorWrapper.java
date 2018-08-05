package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */
public class StatefulProcessorWrapper<T, R> implements Sink<T>, Source<R> {

    private R element;
    private Processor<T, R> processor;

    public StatefulProcessorWrapper(Processor<T, R> processor) {
        this.processor = processor;
    }

    @Override
    public void process(T t) {
        this.element = processor.process(t);
    }

    @Override
    public R get() {
        return element;
    }
}
