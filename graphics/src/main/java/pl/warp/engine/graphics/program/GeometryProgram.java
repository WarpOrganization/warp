package pl.warp.engine.graphics.program;

import com.google.common.io.CharStreams;
import org.lwjgl.opengl.GL20;
import pl.warp.engine.graphics.program.extendedglsl.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.LocalProgramLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 15
 */
public class GeometryProgram extends Program {

    public GeometryProgram(String vertexShaderName, String fragmentShaderName, InputStream geometryShader, ExtendedGLSLProgramCompiler compiler) {
        this(vertexShaderName, fragmentShaderName, toString(geometryShader), compiler);
    }

    private static String toString(InputStream stream) {
        try {
            return CharStreams.toString(new InputStreamReader(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GeometryProgram(String vertexShader, String fragmentShader, String geometryShader, ExtendedGLSLProgramCompiler compiler) {
        this.program = compiler.compile(vertexShader, fragmentShader, geometryShader);
        GL20.glUseProgram(this.program.getGLProgram());
    }

    public GeometryProgram(String programName) {
        this(programName + "/vert", programName + "/frag", programName + "/geom",
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
    }

    @Override
    public void delete() {
        GL20.glDetachShader(program.getGLProgram(), program.getGeometryShader());
        super.delete();
        GL20.glDeleteShader(program.getGeometryShader());
    }
}
