package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.texture.Texture;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 12
 */
public interface Flow<I, O> extends Sink<I>, Source<O>, PipelineElement {

}
