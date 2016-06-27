package pl.warp.engine.graphics.shaders;

import com.google.common.io.CharStreams;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 *         Created 12.11.14 at 20:42
 */
public class ShaderUtil {

    public static final String SUCCESS_LOG = "Success!";

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
        if (log.isEmpty()) log = SUCCESS_LOG;
        System.out.println("Shader name: " + shader + " - " + log);
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
        if (log.isEmpty()) log = SUCCESS_LOG;
        System.out.println("Program name: " + program + " - " + log);
        return program;
    }

}
