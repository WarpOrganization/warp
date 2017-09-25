package pl.warp.engine.graphics.program.extendedglsl;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.loader.ProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor;

import static pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor.ShaderType;

/**
 * @author Jaca777
 * Created 2016-07-20 at 13
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

    public ExtendedGLSLProgram compile(String programName, ProgramAssemblyInfo assemblyInfo) {
        String glslVertexCode = loadAndPreprocess(programName + "/vert", ShaderType.VERTEX);
        String glslFragmentCode = loadAndPreprocess(programName + "/frag", ShaderType.FRAGMENT);
        String glslGeometryCode = assemblyInfo.isGeometryEnabled() ?
                loadAndPreprocess(programName  + "/" + assemblyInfo.getGeometryProgramLocation(), ShaderType.GEOMETRY)
                : null;
        String glslTcsCode = assemblyInfo.isTesselationEnabled() ?
                loadAndPreprocess(programName + "/" + assemblyInfo.getTcsProgramLocation(), ShaderType.TCS)
                : null;
        String glslTesCode = assemblyInfo.isTesselationEnabled() ?
                loadAndPreprocess(programName + "/" + assemblyInfo.getTesProgramLocation(), ShaderType.TES)
                : null;
        return compileGLSL(glslVertexCode, glslFragmentCode, glslGeometryCode, glslTcsCode, glslTesCode);
    }

    private String loadAndPreprocess(String shaderName, ShaderType type) {
        String shaderCode = programLoader.loadProgram(shaderName);
        if (shaderCode == null) throw new ProgramCompilationException(shaderName + " not found");
        else return glslPreprocessor.preprocess(shaderCode, type);
    }

    private ExtendedGLSLProgram compileGLSL(
            String vertexShaderCode,
            String fragmentShaderCode,
            String geometryShaderCode,
            String tcsShaderCode,
            String tesShaderCode) {
        int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int geometryShader = compileShader(GL32.GL_GEOMETRY_SHADER, geometryShaderCode);
        int tcsShader = compileShader(GL40.GL_TESS_CONTROL_SHADER, tcsShaderCode);
        int tesShader = compileShader(GL40.GL_TESS_EVALUATION_SHADER, tesShaderCode);
        int program;
        String[] outNames = ProgramOutputResolver.getOutput(fragmentShaderCode);
        program = GLSLShaderCompiler.createProgram(
                new int[]{vertexShader, tcsShader, tesShader, geometryShader, fragmentShader}, outNames
        );
        return new ExtendedGLSLProgram(fragmentShader, vertexShader, geometryShader, tcsShader, tesShader, program);
    }

    private int compileShader(int shaderType, String shaderCode) {
        return shaderCode == null ? -1
                : GLSLShaderCompiler.compileShader(shaderType, shaderCode);
    }


}
