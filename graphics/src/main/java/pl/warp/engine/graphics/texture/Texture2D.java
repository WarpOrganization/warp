package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


/**
 * @author Jaca777
 *         Created 2016-06-27 at 01
 */
public class Texture2D extends TextureShape2D {

    private static final int DEFAULT_TEXTURE_WRAP_METHOD = GL11.GL_REPEAT;
    private static final int DEFAULT_TEXTURE_RESIZE_FILTER = GL11.GL_LINEAR;

    private int width, height;

    public Texture2D(int type, int texture, int internalformat, int format, boolean mipmap, int width, int height) {
        super(type, texture, internalformat, format, mipmap);
        this.width = width;
        this.height = height;
    }

    protected Texture2D(int texture, int width, int height, int internalFormat, int format, boolean mipmap) {
        super(GL11.GL_TEXTURE_2D, texture, internalFormat, format, mipmap);
        this.width = width;
        this.height = height;
    }

    public Texture2D(int width, int height, int internalFormat, int format, boolean mipmap, ByteBuffer data) {
        super(GL11.GL_TEXTURE_2D, genTexture2D(GL11.GL_TEXTURE_2D, internalFormat, format, width, height, mipmap, data), internalFormat, format, mipmap);
        this.width = width;
        this.height = height;
        setDefaultParams(mipmap);
    }

    private void setDefaultParams(boolean mipmap) {
        setParameter(GL11.GL_TEXTURE_MAG_FILTER, DEFAULT_TEXTURE_RESIZE_FILTER);
        setParameter(GL11.GL_TEXTURE_MIN_FILTER, mipmap ? GL11.GL_LINEAR_MIPMAP_LINEAR : DEFAULT_TEXTURE_RESIZE_FILTER);
        setParameter(GL11.GL_TEXTURE_WRAP_S, DEFAULT_TEXTURE_WRAP_METHOD);
        setParameter(GL11.GL_TEXTURE_WRAP_T, DEFAULT_TEXTURE_WRAP_METHOD);
    }

    public void set(ByteBuffer data) {
        glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data);
        if (mipmap) genMipmap();
    }

    public void copy(Texture2D src) {
        this.type = src.type;
        this.format = src.format;
        this.internalformat = src.internalformat;
        this.height = src.height;
        this.width = src.width;
        this.mipmap = src.mipmap;
        bind();
        glTexImage2D(this.type, 0, this.getInternalformat(), this.getWidth(), this.getHeight(), 0, this.getFormat(), GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL43.glCopyImageSubData(src.getTexture(), src.getType(), 0, 0, 0, 0, this.texture, this.type, 0, 0, 0, 0, src.getWidth(), src.getHeight(), 1);
        if (mipmap) genMipmap();
    }

    private static int genTexture2D(int target, int internalformat, int format, int width, int height, boolean mipmap, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(target, texture);
        glTexImage2D(target, 0, internalformat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(target);
        return texture;
    }

    @Override
    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        glTexImage2D(this.type, 0, this.internalformat, w, h, 0,
                this.format, GL11.GL_BYTE, (ByteBuffer) null);
        if (mipmap) genMipmap();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
