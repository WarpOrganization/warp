package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 2016-08-04 at 02
 */
public class Texture1D extends Texture {
    public Texture1D(int size, int internalformat, int format, boolean mipmap, ByteBuffer data) {
        super(GL11.GL_TEXTURE_1D, genTexture1D(internalformat, format, size, mipmap, data), internalformat, format, mipmap);
    }

    private static int genTexture1D(int internalformat, int format, int size, boolean mipmap, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(GL11.GL_TEXTURE_1D, texture);
        glTexImage1D(GL11.GL_TEXTURE_1D, 0, internalformat, size, 0, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL11.GL_TEXTURE_1D);
        enableDefaultParams(mipmap);
        return texture;
    }

    private static void enableDefaultParams(boolean mipmap) { //TODO sth with defaults
        GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        if (mipmap) GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);
        else GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_1D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
    }
}
