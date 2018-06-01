package net.warpgame.engine.graphics.texture;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 2016-08-04 at 02
 */
public class Texture1D extends Texture {

    private static final int DEFAULT_TEXTURE_RESIZE_FILTER = GL11.GL_LINEAR;
    private static final int DEFAULT_TEXTURE_WRAP_METHOD = GL11.GL_REPEAT;

    public Texture1D(int size, int internalformat, int format, boolean mipmap, ByteBuffer data) {
        super(GL11.GL_TEXTURE_1D, genTexture1D(internalformat, format, size, mipmap, data), internalformat, format);
        setDefaultParams(mipmap);
    }

    private static int genTexture1D(int internalformat, int format, int size, boolean mipmap, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(GL11.GL_TEXTURE_1D, texture);
        glTexImage1D(GL11.GL_TEXTURE_1D, 0, internalformat, size, 0, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL11.GL_TEXTURE_1D);
        return texture;
    }

    private void setDefaultParams(boolean mipmap) {
        setParameter(GL11.GL_TEXTURE_MAG_FILTER, DEFAULT_TEXTURE_RESIZE_FILTER);
        setParameter(GL11.GL_TEXTURE_MIN_FILTER, mipmap ? GL11.GL_LINEAR_MIPMAP_LINEAR : DEFAULT_TEXTURE_RESIZE_FILTER);
        setParameter(GL11.GL_TEXTURE_WRAP_S, DEFAULT_TEXTURE_WRAP_METHOD);
    }
}
