package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL12.glTexImage3D;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
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

    public static int genCubemap(int internalformat, int format, int width, int height, ByteBuffer[] data) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
        for (int i = 0; i < 6; i++) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, internalformat, width, height, 0,
                    format, GL_UNSIGNED_BYTE, data[i]);
        }
        glGenerateMipmap(GL_TEXTURE_CUBE_MAP);
        return texture;
    }

    public static int genTexture2DArray(int internalformat, int format, int width, int height, int size, ByteBuffer[] data) {
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
