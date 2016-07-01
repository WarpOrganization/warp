package pl.warp.engine.graphics.pipeline.builder;

import org.apache.commons.lang3.ArrayUtils;
import pl.warp.engine.graphics.pipeline.*;
import pl.warp.engine.graphics.texture.Texture;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class PipelineBuilder<T extends Texture> {

    private PipelineElement[] elements;
    private Source<T> lastElem;

    private PipelineBuilder(PipelineElement[] elements, Source<T> lastElem) {
        this.elements = elements;
        this.lastElem = lastElem;
    }

    public static <T extends Texture> PipelineBuilder<T> from(Source<T> source) {
        return new PipelineBuilder<>(new PipelineElement[]{source}, source);
    }

    public <O extends Texture> PipelineBuilder<O> via(Flow<T, O> flow) {
        return new PipelineBuilder<>(ArrayUtils.add(elements, flow), flow);
    }

    public Pipeline to(Sink<T> dest) {
        return new Pipeline(ArrayUtils.add(elements, dest));
    }

}
