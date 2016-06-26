package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.texture.Texture;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 12
 */
public interface Source<O extends Texture> extends PipelineElement {
    O getOutput();
}
