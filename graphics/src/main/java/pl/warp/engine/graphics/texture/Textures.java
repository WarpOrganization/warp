package pl.warp.engine.graphics.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 *         Created 2016-12-09 at 19
 */
public class Textures {
    public static int getTexelSizeInBytes(int internalformat) {
        switch (internalformat) {
            case GL11.GL_RGB16:
                return 16 * 3;
            case GL11.GL_RGB8:
                return 8 * 3;
            case GL11.GL_RGBA16:
                return 16 * 4;
            case GL11.GL_RGBA8:
                return 8 * 4;
            case GL30.GL_RGB32F:
                return 32 * 3;
            case GL30.GL_RGB16F:
                return 16 * 3;
            case GL30.GL_RGBA32F:
                return 32 * 4;
            case GL30.GL_RGBA16F:
                return 16 * 4;
            default:
                throw new IllegalArgumentException("Unsupported internalfromat: " + internalformat);
        }
    }
}
