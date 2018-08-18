package net.warpgame.engine.graphics.resource.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-07-18 at 15
 */
public final class ImageData {
    private ByteBuffer buffer;
    private int w, h;
    private int format;

    public ImageData(ByteBuffer buffer, int w, int h, int format) {
        this.buffer = buffer;
        this.w = w;
        this.h = h;
        this.format = format;
    }

    public ByteBuffer getData() {
        return buffer;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getFormat() {
        return format;
    }

    public int getInternalFormat(){
        switch (format){
            case GL12.GL_BGRA:
            case GL11.GL_RGB: return GL11.GL_RGB16;
            case GL11.GL_RGBA: return GL11.GL_RGBA16;
            default: throw new RuntimeException("OpenGL doesn't support this format");
        }
    }
}
