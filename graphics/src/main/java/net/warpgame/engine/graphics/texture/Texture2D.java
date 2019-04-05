package net.warpgame.engine.graphics.texture;

import net.warpgame.engine.graphics.resource.texture.ImageData;

import java.nio.ByteBuffer;


/**
 * @author Jaca777
 *         Created 2016-06-27 at 01
 */
public class Texture2D{

    /*private static final int DEFAULT_TEXTURE_WRAP_METHOD = GL11.GL_REPEAT;
    private static final int DEFAULT_TEXTURE_RESIZE_FILTER = GL11.GL_LINEAR;*/

    private int width, height;

    public Texture2D(int type, int texture, int internalformat, int format, int width, int height) {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected Texture2D(int texture, int width, int height, int internalFormat, int format) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Texture2D(int width, int height, int internalFormat, int format, boolean mipmap, ByteBuffer data) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Texture2D(ImageData imageData){
        throw new UnsupportedOperationException("Not implemented");
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void genMipmap() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void delete() {
        throw new UnsupportedOperationException();
    }
}
