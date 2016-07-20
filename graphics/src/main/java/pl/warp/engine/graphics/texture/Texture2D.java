package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import pl.warp.engine.graphics.texture.Texture;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


/**
 * @author Jaca777
 *         Created 2016-06-27 at 01
 */
public class Texture2D extends Texture {
    protected Texture2D(int texture, int width, int height, int internalFormat, int format, boolean mipmap) {
        super(GL11.GL_TEXTURE_2D, texture, width, height, internalFormat, format, mipmap);
    }

    public Texture2D(int width, int height, int internalFormat, int format, boolean mipmap, ByteBuffer data) {
        super(GL11.GL_TEXTURE_2D, genTexture2D(GL11.GL_TEXTURE_2D, internalFormat, format, width, height, mipmap, data), width, height, internalFormat, format, mipmap);
        enableDefaultParams(mipmap);
    }

    private void enableDefaultParams(boolean mipmap) { //TODO sth with defaults
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        if (mipmap) GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        else GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
    }

    public void set(ByteBuffer data) {
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, data);
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
        GL11.glTexImage2D(this.type, 0, this.getInternalformat(), this.getWidth(), this.getHeight(), 0, this.getFormat(), GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
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
}
