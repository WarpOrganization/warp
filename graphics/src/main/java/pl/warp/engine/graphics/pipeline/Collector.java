package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.texture.Texture;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public interface Collector <I extends List<? extends Texture>, O extends Texture> extends Source<O> {
    void setInput(I textures);
}
