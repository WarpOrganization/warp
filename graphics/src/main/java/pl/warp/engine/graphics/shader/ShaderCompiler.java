package pl.warp.engine.graphics.shader;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 *         Created 12.11.14 at 20:42
 */
public class ShaderCompiler {

    private static Logger logger = Logger.getLogger("ShaderCompiler");

    /**
     * @param type       Type of the shader.
     * @param shaderCode Code of the shader.
     * @return The shader's name.
     */
    public static int compileShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        String log = glGetShaderInfoLog(shader, 65536); //Can either be empty, or contain a message (even if operation was successful).
        if (log.isEmpty()) logger.info("Shader " + shader + " successfully compiled.");
        if (log.contains("error")) {
            logger.error("Failed to compile shader " + shader + ". Cause: " + log);
            throw new ShaderCompilationException(log);
        } else if(log.contains("warn")){
            logger.warn("Warnings while compiling shader " + shader + ". Cause: " + log);
        }
        return shader;
    }

    /**
     * Links shaders into a program.
     *
     * @param vertexShader   Vertex shader's name.
     * @param fragmentShader Fragment shader's name.
     * @return The program's name.
     */
    public static int createProgram(int vertexShader, int fragmentShader, String[] outNames) {
        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
        glLinkProgram(program);
        String log = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        if (log.isEmpty()) {
            logger.info("Program " + program + " successfully linked.");
        } else if (log.contains("error")) {
            logger.error("Failed to link program " + program + ". Cause: " + log);
            throw new ShaderCompilationException(log);
        } else if(log.contains("warning")){
            logger.warn("Warnings while linking program " + program + ". Cause: " + log);
        }
        return program;
    }

}
