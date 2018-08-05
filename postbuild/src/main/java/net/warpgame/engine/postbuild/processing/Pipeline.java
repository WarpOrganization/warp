package net.warpgame.engine.postbuild.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */
public class Pipeline<T> {

    private static final Logger logger = LoggerFactory.getLogger(Pipeline.class);

    private Source<T> source;
    private List<Processor> processors;
    private Sink sink;

    private Pipeline(Source source) {
        this.source = source;
        this.processors = new ArrayList<>();
    }

    public static <T> PipelineBuilder<T> from(Source<T> source) {
        Pipeline pipeline = new Pipeline(source);
        return new PipelineBuilder<>(pipeline);
    }

    public void run() {
        verifyState();
        try {
            Object elem = source.get();
            for (Processor processor : processors) {
                elem = processor.process(elem);
            }
            sink.process(elem);
        } catch (Exception e) {
            logger.error("Error occurred while running pipeline", e);
        }
    }

    private void verifyState() {
        if(isNull(source)){
            throw new IllegalStateException("Pipeline source is null");
        }
        if(isNull(sink)){
            throw new IllegalStateException("Sink is null");
        }
    }

    public static class PipelineBuilder<T> {

        private Pipeline pipeline;

        public PipelineBuilder(Pipeline pipeline) {
            this.pipeline = pipeline;
        }

        public <R> PipelineBuilder<R> via(Processor<T, R> processor) {
            pipeline.processors.add(processor);
            return new PipelineBuilder<>(pipeline);
        }

        public Pipeline to(Sink<T> sink) {
            pipeline.sink = sink;
            return pipeline;
        }

    }
}
