package pl.warp.engine.graphics.pipeline.impl;

import pl.warp.engine.graphics.pipeline.Sink;
import pl.warp.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 23
 */
public class OnScreenSink implements Sink<Texture2D> {

    private Texture2D input;

    public OnScreenSink(Texture2D input) {
        this.input = input;
    }

    @Override
    public void setInput(Texture2D input) {
        this.input = input;
    }

    @Override
    public void update() {
        //TODO
        throw new UnsupportedOperationException();
    }
}
