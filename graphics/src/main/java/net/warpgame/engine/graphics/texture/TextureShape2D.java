package net.warpgame.engine.graphics.texture;

/**
 * @author Jaca777
 *         Created 2016-08-04 at 02
 */
public abstract class TextureShape2D extends Texture {
    public TextureShape2D(int type, int texture, int internalformat, int format) {
        super(type, texture, internalformat, format);
    }

    public abstract void resize(int w, int h, boolean mipmap);

    public abstract int getWidth();

    public abstract int getHeight();
}
