package net.warpgame.engine.graphics.program;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL30;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Jaca777
 * Created 12.11.14 at 20:42
 */
public class GLSLShaderCompiler {

    private static Logger logger = Logger.getLogger("GLSLShaderCompiler");

    /**
     * @param type       Type of the shader.
     * @param shaderCode Code of the shader.
     * @return The shader's value.
     */
    public static int compileShader(int type, String shaderCode, String programName) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        String nameInfo = " (of " + programName + ")";
        String log = glGetShaderInfoLog(shader, 65536); //Can either be empty, or contain a message (even if operation was successful).
        if (log.isEmpty()) logger.info("Shader " + shader + nameInfo + " successfully compiled.");
        else if (log.contains("error")) { //If it looks stupid but works it ain't stupid
            logger.error("Failed to compile shader " + shader + nameInfo + ". Cause: " + log);
            tryLogErrorLine(log, shaderCode);
            throw new ShaderCompilationException(log);
        } else if (log.contains("warn")) {
            logger.warn("Warnings while compiling shader " + shader + nameInfo + ". Cause: " + log);
        } else {
            logger.warn("Shader " + shader + nameInfo + " compilation returned a message: " + log);
        }
        return shader;
    }

    private static final Pattern PATTERN = Pattern.compile("(\\d*):(\\d*):");

    private static void tryLogErrorLine(String log, String shaderCode) {
        Matcher matcher = PATTERN.matcher(log);
        boolean found = matcher.find();
        if (found) {
            int line = Integer.parseInt(matcher.group(2));
            int character = Integer.parseInt(matcher.group(1));
            String[] lines = shaderCode.split("\n");
            String errorLine = lines[line - 1];
            String characterPointer = Strings.repeat(" ", character) + "^";
            logger.error("Error line: ");
            logger.error(errorLine);
            logger.error(characterPointer);
        }
    }

    /**
     * Links shaders into a program. Skips shader names that equal -1.
     *
     * @return The program's value.
     */
    public static int createProgram(int[] shaders, String[] outNames, String programName) {
        int program = glCreateProgram();
        for (int shader : shaders)
            if (shader != -1) glAttachShader(program, shader);
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
        glLinkProgram(program);
        checkProgramLinkInfo(program, programName);
        return program;
    }

    private static void checkProgramLinkInfo(int program, String programName) {
        String info = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        String nameInfo = " (instance of " + programName + ")";
        if (info.isEmpty()) {
            logger.info("Program " + program + nameInfo + " successfully linked.");
        } else if (info.contains("error")) {
            logger.error("Failed to link program " + program + nameInfo + ". Cause: " + info);
            throw new ShaderCompilationException(info);
        } else if (info.contains("warning")) {
            logger.warn("Warnings while linking program " + program + nameInfo + ". Cause: " + info);
        }
    }
}

