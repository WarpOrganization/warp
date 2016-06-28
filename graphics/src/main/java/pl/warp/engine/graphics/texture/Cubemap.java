package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 20.12.14 at 21:53
 */
public class Cubemap extends Texture{
    public Cubemap(int type, int texture, int width, int height, int internalformat, int format, boolean mipmap) {
        super(type, texture, width, height, internalformat, format, mipmap);
    }

    public Cubemap(int width, int height, ByteBuffer[] data) {
        super(GL13.GL_TEXTURE_CUBE_MAP, genCubemap(GL11.GL_RGBA, GL11.GL_RGBA, width, height, data), width, height, GL11.GL_RGBA, GL11.GL_RGBA, false);
    }

    @Override
    public void resize(int w, int h) {
        bind();
        for (int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, internalformat, width, height, 0,
                    format, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        }
        if(mipmap) glGenerateMipmap(this.type);
    }

    private static int genCubemap(int internalformat, int format, int width, int height, ByteBuffer[] data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
        for (int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, internalformat, width, height, 0,
                    format, GL_UNSIGNED_BYTE, data[i]);
        }
        glGenerateMipmap(GL_TEXTURE_CUBE_MAP);
        return texture;
    }
}
