package net.warpgame.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
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
public class Cubemap{
    private int width;
    private int height;

    public Cubemap(int width, int height, ByteBuffer[] data) {
       throw new UnsupportedOperationException("Not implemented");
    }

    private void setDefaultParams() {
    }

    public void resize(int w, int h, boolean mipmap) {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static int genCubemap(int internalformat, int format, int width, int height, ByteBuffer[] data, boolean mipmap) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
