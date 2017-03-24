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
public class Cubemap extends TextureShape2D {
    private int width;
    private int height;

    public Cubemap(int width, int height, ByteBuffer[] data) {
        super(GL13.GL_TEXTURE_CUBE_MAP, genCubemap(GL11.GL_RGBA, GL11.GL_RGBA, width, height, data), GL11.GL_RGBA, GL11.GL_RGBA, false);
        this.width = width;
        this.height = height;
        setDefaultParams();
    }

    private void setDefaultParams() {
        setParameter(GL11.GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        setParameter(GL11.GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        setParameter(GL11.GL_TEXTURE_WRAP_S, GL_REPEAT);
    }

    public void resize(int w, int h) {
        bind();
        for (int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, internalformat, width, height, 0,
                    format, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        }
        if(mipmap) glGenerateMipmap(this.type);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
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
