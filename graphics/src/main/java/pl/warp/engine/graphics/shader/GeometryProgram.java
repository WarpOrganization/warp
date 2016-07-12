package pl.warp.engine.graphics.shader;

import com.google.common.io.CharStreams;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 15
 */
public class GeometryProgram extends Program {
    private int geometryShader;

    public GeometryProgram(InputStream geometryShader, InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        try {
            this.geometryShader = ShaderCompiler.compileShader(GL32.GL_GEOMETRY_SHADER, CharStreams.toString(new InputStreamReader(geometryShader)));
            this.vertexShader = ShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, CharStreams.toString(new InputStreamReader(vertexShader)));
            this.fragmentShader = ShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, CharStreams.toString(new InputStreamReader(fragmentShader)));
            this.program = ShaderCompiler.createProgram(new int[]{this.geometryShader, this.vertexShader, this.fragmentShader}, outNames);
            GL20.glUseProgram(this.program);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete() {
        GL20.glDetachShader(program, geometryShader);
        super.delete();
        GL20.glDeleteShader(geometryShader);
    }
}
