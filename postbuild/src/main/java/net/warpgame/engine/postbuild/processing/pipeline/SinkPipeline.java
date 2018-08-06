package net.warpgame.engine.postbuild.processing.pipeline;

import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.Source;

/**
 * @author Jaca777
 * Created 2018-08-06 at 18
 */
public class SinkPipeline<T> implements Sink<T> {

    private Sink<T> sink;

    public SinkPipeline(Sink<T> sink) {
        this.sink = sink;
    }

    public RunnablePipeline from(Source<T> source) {
        return new RunnablePipeline(source, sink);
    }

    @Override
    public void process(T t) {
        sink.process(t);
    }
}
