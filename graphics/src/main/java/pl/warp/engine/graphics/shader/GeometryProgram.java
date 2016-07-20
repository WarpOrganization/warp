package pl.warp.engine.graphics.shader;

import com.google.common.io.CharStreams;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 15
 */
public class GeometryProgram extends Program {

    public GeometryProgram(InputStream vertexShader, InputStream fragmentShader, InputStream geometryShader, ConstantField field) {
        this(toString(vertexShader), toString(fragmentShader), toString(geometryShader), field);
    }

    public GeometryProgram(InputStream vertexShader, InputStream fragmentShader, InputStream geometryShader) {
        this(vertexShader, fragmentShader, geometryShader, ConstantField.EMPTY_CONSTANT_FIELD);
    }

    private static String toString(InputStream stream) {
        try {
            return CharStreams.toString(new InputStreamReader(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GeometryProgram(String vertexShader, String fragmentShader, String geometryShader, ConstantField field) {
        ExtendedGLSLProgramCompiler compiler = new ExtendedGLSLProgramCompiler(vertexShader, fragmentShader, field);
        compiler.useGeometryShader(geometryShader);
        this.program = compiler.compile();
        GL20.glUseProgram(this.program.getGLProgram());
    }

    public GeometryProgram(String vertexShader, String fragmentShader, String geometryShader) {
        this(vertexShader, fragmentShader, geometryShader, ConstantField.EMPTY_CONSTANT_FIELD);
    }


    @Override
    public void delete() {
        GL20.glDetachShader(program.getGLProgram(), program.getGeometryShader());
        super.delete();
        GL20.glDeleteShader(program.getGeometryShader());
    }
}
