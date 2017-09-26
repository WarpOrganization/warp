package pl.warp.engine.graphics.program;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 *         Created 12.11.14 at 20:42
 */
public class GLSLShaderCompiler {

    private static Logger logger = Logger.getLogger("GLSLShaderCompiler");

    /**
     * @param type       Type of the shader.
     * @param shaderCode Code of the shader.
     * @return The shader's value.
     */
    public static int compileShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        String log = glGetShaderInfoLog(shader, 65536); //Can either be empty, or contain a message (even if operation was successful).
        if (log.isEmpty()) logger.info("Shader " + shader + " successfully compiled.");
        else if (log.contains("error")) {
            logger.error("Failed to compile shader " + shader + ". Cause: " + log);
            throw new ShaderCompilationException(log);
        } else if (log.contains("warn")) {
            logger.warn("Warnings while compiling shader " + shader + ". Cause: " + log);
        } else  {
            logger.warn("Shader " + shader + " compilation returned a message: " + log);
        }
        return shader;
    }

    /**
     * Links shaders into a program. Skips shader names that equal -1.
     *
     * @return The program's value.
     */
    public static int createProgram(int[] shaders, String[] outNames) {
        int program = glCreateProgram();
        for (int shader : shaders)
            if(shader != -1) glAttachShader(program, shader);
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
        glLinkProgram(program);
        checkProgramLinkInfo(program);
        return program;
    }

    private static void checkProgramLinkInfo(int program) {
        String info = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        if (info.isEmpty()) {
            logger.info("Program " + program + " successfully linked.");
        } else if (info.contains("error")) {
            logger.error("Failed to link program " + program + ". Cause: " + info);
            throw new ShaderCompilationException(info);
        } else if (info.contains("warning")) {
            logger.warn("Warnings while linking program " + program + ". Cause: " + info);
        }
    }
}

