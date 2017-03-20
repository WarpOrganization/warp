package pl.warp.engine.graphics.pipeline.builder;

import org.apache.commons.lang3.ArrayUtils;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.pipeline.*;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class PipelineBuilder<T> {

    private final PipelineElement[] elements;
    private final Source<T> lastElem;

    private PipelineBuilder(PipelineElement[] elements, Source<T> lastElem) {
        this.elements = elements;
        this.lastElem = lastElem;
    }

    public static <T> PipelineBuilder<T> from(Source<T> source) {
        return new PipelineBuilder<>(new PipelineElement[]{source}, source);
    }

    public <O> PipelineBuilder<O> via(Flow<T, O> flow) {
        return new PipelineBuilder<>(ArrayUtils.add(elements, flow), flow);
    }

    public Pipeline to(Sink<T> dest, Graphics graphics) {
        return new Pipeline(ArrayUtils.add(elements, dest), graphics);
    }

}
