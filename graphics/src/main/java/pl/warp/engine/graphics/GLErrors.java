package pl.warp.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 *         Created 2016-07-30 at 23
 */
public class GLErrors {
    public static void checkOGLErrors() {
        int error;
        if ((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
            throw new OGLException(GLErrors.toString(error));
    }

    public static String toString(int error) {
        switch (error) {
            case GL11.GL_INVALID_ENUM:
                return "Invalid enum";
            case GL11.GL_INVALID_VALUE:
                return "Invalid value";
            case GL11.GL_INVALID_OPERATION:
                return "Invalid operation";
            case GL11.GL_STACK_OVERFLOW:
                return "Stack overflow";
            case GL11.GL_STACK_UNDERFLOW:
                return "Stack underflow";
            case GL11.GL_OUT_OF_MEMORY:
                return "Out of memory";
            case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
                return "Invalid framebuffer operation";
            default:
                return "Unknown error code: " + error;
        }
    }
}
