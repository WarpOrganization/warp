package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 20.12.14 at 21:53
 */
public class Texture2DArray extends Texture {
    private int size;

    protected Texture2DArray(int texture, int width, int height, int size, int internalformat, int format, boolean mipmap) {
        super(GL30.GL_TEXTURE_2D_ARRAY, texture, width, height, internalformat, format, mipmap);
        this.size = size;
    }


    public Texture2DArray(int width, int height, int size, ByteBuffer[] data) {
        this(width, height, size, data, GL11.GL_RGBA, GL11.GL_RGBA);
    }

    public Texture2DArray(int width, int height, int size, ByteBuffer[] data, int internalformat, int format) {
        super(GL30.GL_TEXTURE_2D_ARRAY, genTexture2DArray(internalformat, format, width, height, size, data), width, height, internalformat, format, true);
        GL11.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL11.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_REPEAT);
        GL11.glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        this.size = size;
    }

    public Texture2DArray(int texture, int width, int height, boolean mipmap, int size) {
        super(GL30.GL_TEXTURE_2D_ARRAY, texture, width, height, GL11.GL_RGBA, GL11.GL_RGBA, mipmap);
        this.size = size;
    }

    /**
     * Sets the texture.
     * @param data Must be the same size as texture!
     */
    public void set(int offset, ByteBuffer data) {
        glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, offset, width, height, 1, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
    }

    @Override
    public void resize(int w, int h) {
        this.resize(w,h,size);
        if(mipmap) glGenerateMipmap(this.type);
    }

    public void resize(int w, int h, int size){
        GL12.glTexImage3D(this.type, 0, this.internalformat, w, h, size, 0,
                this.format, GL11.GL_BYTE, (ByteBuffer) null);
        this.width = w;
        this.height = h;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    private static int genTexture2DArray(int internalformat, int format, int width, int height, int size, ByteBuffer[] data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_ARRAY, texture);
        glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, internalformat, width, height, size, 0, format, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        for (int i = 0; i < size; i++) {
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, i, width, height, 1, format, GL_UNSIGNED_BYTE, data[i]);
        }
        glGenerateMipmap(GL_TEXTURE_2D_ARRAY);
        return texture;
    }
}