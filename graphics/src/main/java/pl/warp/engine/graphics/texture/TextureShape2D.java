package pl.warp.engine.graphics.texture;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-08-04 at 02
 */
public abstract class TextureShape2D extends Texture {
    public TextureShape2D(int type, int texture, int internalformat, int format, boolean mipmap) {
        super(type, texture, internalformat, format, mipmap);
    }

    public abstract void resize(int w, int h);

    public abstract int getWidth();

    public abstract int getHeight();
}
