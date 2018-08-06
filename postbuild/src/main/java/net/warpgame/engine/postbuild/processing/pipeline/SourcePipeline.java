package net.warpgame.engine.postbuild.processing.pipeline;

import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.Source;

/**
 * @author Jaca777
 * Created 2018-08-06 at 18
 */
public class SourcePipeline<T> implements Source<T> {

    private Source<T> source;

    public SourcePipeline(Source source) {
        this.source = source;
    }

    @Override
    public T get() throws Exception {
        return source.get();
    }

    public <R> SourcePipeline<R> via(Processor<T, R> processor) {
        return new SourcePipeline<>(() -> processor.process(source.get()));
    }

    public RunnablePipeline to(Sink<T> sink) {
        return new RunnablePipeline(source, sink);
    }
}
