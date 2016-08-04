package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
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

    protected boolean mipmap;

    public Texture(int type, int texture, int internalformat, int format, boolean mipmap) {
        this.type = type;
        this.texture = texture;
        this.internalformat = internalformat;
        this.format = format;
        this.mipmap = mipmap;
    }

    public void enableAnisotropy(int level) {
        if (isAnisotropySupported())
            glTexParameteri(type, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, level);
        else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    public void disableAnisotropy() {
        if (isAnisotropySupported())
            glTexParameteri(type, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 0);
        else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    private static boolean isAnisotropySupported() {
        return GL.getCapabilities().GL_EXT_texture_filter_anisotropic;
    }


    public void setParameter(int paramName, int param) {
        GL11.glTexParameteri(type, paramName, param);
    }

    protected void genMipmap() {
        glGenerateMipmap(this.type);
    }

    public void bind() {
        GL11.glBindTexture(this.type, this.texture);
    }

    public void unbind() {
        GL11.glBindTexture(this.type, 0);
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

    public boolean isMipmap() {
        return mipmap;
    }
}
