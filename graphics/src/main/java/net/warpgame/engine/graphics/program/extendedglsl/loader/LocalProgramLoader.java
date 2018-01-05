package net.warpgame.engine.graphics.program.extendedglsl.loader;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Jaca777
 *         Created 2016-08-07 at 17
 */
public class LocalProgramLoader implements ProgramLoader {

    public static final String DEFAULT_PROGRAM_PATH = "net/warpgame/engine/graphics/program/";
    public static final String DEFAULT_PROGRAM_EXTENSION = ".glsl";

    public static final LocalProgramLoader DEFAULT_LOCAL_PROGRAM_LOADER = new LocalProgramLoader(DEFAULT_PROGRAM_PATH, DEFAULT_PROGRAM_EXTENSION);

    private String programPath;
    private String programExtension;

    public LocalProgramLoader(String programPath, String programExtension) {
        this.programPath = programPath;
        this.programExtension = programExtension;
    }

    @Override
    public String loadProgram(String programName) {
        String fullName = toFullName(programName);
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fullName);
            if (resource == null) return null;
            else return toString(resource);
        } catch (IOException e) {
            return null;
        }
    }

    private String toFullName(String shaderName) {
        return programPath + shaderName + programExtension;
    }

    private String toString(InputStream stream) throws IOException {
        return CharStreams.toString(new InputStreamReader(stream));
    }

}
