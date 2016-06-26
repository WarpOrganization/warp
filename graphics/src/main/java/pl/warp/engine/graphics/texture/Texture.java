package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 12
 */
public abstract class Texture {
    protected int type,
            texture,
            internalformat,
            format;
    protected int width,
            height;
    protected boolean mipmap;

    public Texture(int type, int texture, int internalformat, int format, int width, int height, boolean mipmap) {
        this.type = type;
        this.texture = texture;
        this.internalformat = internalformat;
        this.format = format;
        this.width = width;
        this.height = height;
        this.mipmap = mipmap;
    }

    public void setAnisotropy(boolean anisotropy) {
        if(anisotropy) TextureUtil.enableAnisotropy(this.type, this.texture);
        else TextureUtil.disableAnisotropy(this.type);
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        GL11.glTexImage2D(this.type, 0, this.internalformat, w, h, 0,
                this.format, GL11.GL_BYTE, (ByteBuffer) null);
        if (mipmap) genMipmap();
    }

    protected void genMipmap() {
        glGenerateMipmap(this.type);
    }

    public void bind() {
        GL11.glBindTexture(this.type, this.texture);
    }

    public void delete() {
        GL11.glDeleteTextures(texture);
    }

    public int getType() {
        return type;
    }

    public int getTexture() {
        return texture;
    }

    public int getInternalformat() {
        return internalformat;
    }

    public int getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isMipmap() {
        return mipmap;
    }
}
