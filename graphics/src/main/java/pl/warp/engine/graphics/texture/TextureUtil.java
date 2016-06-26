package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 01
 */
public class TextureUtil {

    public static int genTexture2D(int target, int internalformat, int format, int width, int height, boolean mipmap, ByteBuffer data) {
        int texture = glGenTextures();
        glBindTexture(target, texture);
        glTexImage2D(target, 0, internalformat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        if (mipmap) glGenerateMipmap(target);
        return texture;
    }

    public static void enableAnisotropy(int target, int level) {
        if (isAnisotropySupported())
            glTexParameteri(target, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, level);
        else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    public static void disableAnisotropy(int target) {
        if (isAnisotropySupported())
            glTexParameteri(target, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 0);
        else throw new RuntimeException("Anisotropic filtering is not supported.");
    }

    public static boolean isAnisotropySupported() {
        return GL.getCapabilities().GL_EXT_texture_filter_anisotropic;
    }
}
