package net.warpgame.engine.postbuild.processing.pipeline;

import net.warpgame.engine.postbuild.processing.Processor;
import net.warpgame.engine.postbuild.processing.Sink;
import net.warpgame.engine.postbuild.processing.Source;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */
public class Pipeline {

    public static <T> SourcePipeline<T> from(Source<T> source) {
        return new SourcePipeline<>(source);
    }

    public static <T, R> ProcessorPipeline<T, R> via(Processor<T, R> processor) {
        return new ProcessorPipeline<>(processor);
    }


    public static <T> SinkPipeline<T> to(Sink<T> sink) {
        return new SinkPipeline<>(sink);
    }


}
