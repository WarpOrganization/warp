package pl.warp.engine.graphics.program.extendedglsl;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;
import pl.warp.engine.graphics.program.extendedglsl.loader.ProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor;

/**
 * @author Jaca777
 *         Created 2016-07-20 at 13
 */
public class ExtendedGLSLProgramCompiler {

    private ProgramLoader programLoader;
    private ExtendedGLSLPreprocessor glslPreprocessor;

    public ExtendedGLSLProgramCompiler(ConstantField constants, ProgramLoader programLoader) {
        this.programLoader = programLoader;
        this.glslPreprocessor = new ExtendedGLSLPreprocessor(constants, programLoader);
    }

    public ExtendedGLSLProgram compile(String vertexShaderName, String fragmentShaderName, String geometryShaderName) {
        String glslVertexCode = loadAndPreprocess(vertexShaderName);
        if(glslVertexCode == null) throw new ProgramCompilationException("Vertex shader not found: " + vertexShaderName);
        String glslFragmentCode = loadAndPreprocess(fragmentShaderName);
        if (glslFragmentCode == null) throw new ProgramCompilationException("Fragment shader not found: " + fragmentShaderName);
        String glslGeometryCode = loadGeometry(geometryShaderName);
        return compileGLSL(glslVertexCode, glslFragmentCode, glslGeometryCode);
    }

    private String loadAndPreprocess(String shaderName) {
        String vertexShaderCode = programLoader.loadProgram(shaderName);
        if (vertexShaderCode == null) return null;
        else return glslPreprocessor.preprocess(vertexShaderCode);
    }

    private String loadGeometry(String geometryShaderName) {
        if(geometryShaderName == null) return null;
        else {
            String glslGeometryCode = loadAndPreprocess(geometryShaderName);
            if(glslGeometryCode == null)
                throw new ProgramCompilationException("Geometry shader not found: " + geometryShaderName);
            else return glslGeometryCode;
        }
    }

    private ExtendedGLSLProgram compileGLSL(String vertexShaderCode, String fragmentShaderCode, String geometryShaderCode) {
        int vertexShader = GLSLShaderCompiler.compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GLSLShaderCompiler.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int geometryShader = 0;
        int glslProgram;
        String[] outNames = ProgramOutputResolver.getOutput(fragmentShaderCode);
        if (geometryShaderCode != null) {
            geometryShader = GLSLShaderCompiler.compileShader(GL32.GL_GEOMETRY_SHADER, geometryShaderCode);
            glslProgram = GLSLShaderCompiler.createProgram(new int[]{geometryShader, vertexShader, fragmentShader}, outNames);
        } else glslProgram = GLSLShaderCompiler.createProgram(new int[]{vertexShader, fragmentShader}, outNames);
        return new ExtendedGLSLProgram(fragmentShader, vertexShader, geometryShader, glslProgram);
    }


}
