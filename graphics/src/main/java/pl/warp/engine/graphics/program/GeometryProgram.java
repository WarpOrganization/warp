package pl.warp.engine.graphics.program;

import org.lwjgl.opengl.GL20;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;

/**
 * @author Jaca777
 *         Created 2016-07-12 at 15
 */
public class GeometryProgram extends Program {

    private String geomShaderName;

    public GeometryProgram(String vertexShaderName, String fragmentShaderName, String geometryShaderName, ExtendedGLSLProgramCompiler compiler) {
        this.compiler = compiler;
        this.vertexShaderName = vertexShaderName;
        this.fragmentShaderName = fragmentShaderName;
        this.geomShaderName = geometryShaderName;
        this.compiler = compiler;
    }

    public GeometryProgram(String programName) {
        this(programName + "/vert", programName + "/frag", programName + "/geom",
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
    }

    @Override
    public void compile() {
        this.program = compiler.compile(vertexShaderName, fragmentShaderName, geomShaderName);
        GL20.glUseProgram(this.program.getGLProgram());
    }

    @Override
    public void delete() {
        GL20.glDetachShader(program.getGLProgram(), program.getGeometryShader());
        super.delete();
        GL20.glDeleteShader(program.getGeometryShader());
    }
}
