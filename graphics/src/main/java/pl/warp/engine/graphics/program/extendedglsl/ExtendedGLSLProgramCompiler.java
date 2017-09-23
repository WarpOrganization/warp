package pl.warp.engine.graphics.program.extendedglsl;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.loader.ProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedGLSLProgramCompiler {

    public static final ExtendedGLSLProgramCompiler DEFAULT_COMPILER =
            new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER);

    private ProgramLoader programLoader;
    private ExtendedGLSLPreprocessor glslPreprocessor;

    public ExtendedGLSLProgramCompiler(ConstantField constants, ProgramLoader programLoader) {
        this.programLoader = programLoader;
        this.glslPreprocessor = new ExtendedGLSLPreprocessor(constants, programLoader);
    }

    public ExtendedGLSLProgram compile(String programName) {
        String glslVertexCode = loadAndPreprocess(programName + "/vert");
        if(glslVertexCode == null) throw new ProgramCompilationException("Vertex shader not found for: " + programName);
        String glslFragmentCode = loadAndPreprocess(programName + "/frag");
        if (glslFragmentCode == null) throw new ProgramCompilationException("Fragment shader not found for: " + programName);
        String glslGeometryCode = loadAndPreprocess(programName + "/geom");
        String glslTesCode = loadAndPreprocess(programName + "/tes");
        String glslTcsCode = loadAndPreprocess(programName + "/tcs");
        return compileGLSL(glslVertexCode, glslFragmentCode, glslGeometryCode, glslTesCode, glslTcsCode);
    }

    private String loadAndPreprocess(String shaderName) {
        String shaderCode = programLoader.loadProgram(shaderName);
        if (shaderCode == null) return null;
        else return glslPreprocessor.preprocess(shaderCode);
    }

    private ExtendedGLSLProgram compileGLSL(
            String vertexShaderCode,
            String fragmentShaderCode,
            String geometryShaderCode,
            String tcsShaderCode,
            String tesShaderCode) {
        int vertexShader = GLSLShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GLSLShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int geometryShader = geometryShaderCode == null ? -1
                : GLSLShaderCompiler.compileShader(GL32.GL_GEOMETRY_SHADER, geometryShaderCode);
        int tcsShader = tcsShaderCode == null ? -1
                : GLSLShaderCompiler.compileShader(GL40.GL_TESS_CONTROL_SHADER, tcsShaderCode);
        int tesShader = tesShaderCode == null ? -1
                : GLSLShaderCompiler.compileShader(GL40.GL_TESS_EVALUATION_SHADER, tesShaderCode);
        int program;
        String[] outNames = ProgramOutputResolver.getOutput(fragmentShaderCode);
        program = GLSLShaderCompiler.createProgram(
                new int[]{vertexShader, tcsShader, tesShader, geometryShader, fragmentShader}, outNames
        );
        return new ExtendedGLSLProgram(fragmentShader, vertexShader, geometryShader, tcsShader, tesShader, program);
    }


}
