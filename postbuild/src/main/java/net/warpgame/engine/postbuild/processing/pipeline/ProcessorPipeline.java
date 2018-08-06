package net.warpgame.engine.postbuild.processing.pipeline;

import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.Source;

/**
 * @author Jaca777
 * Created 2018-08-06 at 18
 */
public class ProcessorPipeline<T, R> implements Processor<T,R> {

    private Processor<T, R> processor;

    public ProcessorPipeline(Processor<T, R> processor) {
        this.processor = processor;
    }

    public SourcePipeline<R> from(Source<T> sink) {
        return new SourcePipeline<>(() -> processor.process(sink.get()));
    }

    public <Q> ProcessorPipeline<T, Q> via(Processor<R, Q> processor) {
        return new ProcessorPipeline<>(t -> processor.process(this.processor.process(t)));
    }

    public SinkPipeline<T> to(Sink<R> sink) {
        return new SinkPipeline<>(t -> sink.process(processor.process(t)));
    }

    @Override
    public R process(T t) {
        return processor.process(t);
    }
}
