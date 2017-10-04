package pl.warp.engine.graphics.rendering.culling;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL43;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor;
import pl.warp.engine.graphics.rendering.scene.mesh.SceneMesh;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Jaca777
 * Created 2017-09-30 at 23
 */


@Service
public class BoundingBoxCalculator {

    private static final String SHADER_LOCATION = "bbox/reduce.glsl";
    private static final int LOCAL_SIZE = 32;
    private static final int INVOCATION_SIZE = 32;


    private int program;

    public void initialize() {
        compileProgram();

    }

    protected void compileProgram() {
        String processedSource = loadSource();
        int shader = GLSLShaderCompiler.compileShader(GL43.GL_COMPUTE_SHADER, processedSource);
        this.program = GLSLShaderCompiler.createProgram(new int[] {shader}, new String[0]);
    }

    private String loadSource() {
        ConstantField constantField = new ConstantField()
                .set("LOCAL_SIZE", LOCAL_SIZE)
                .set("INVOCATION_SIZE", INVOCATION_SIZE);
        LocalProgramLoader loader = LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER;
        ExtendedGLSLPreprocessor glslPreprocessor = new ExtendedGLSLPreprocessor(constantField, loader);
        String shaderCode = getShaderSource();
        ExtendedGLSLPreprocessor.ShaderType type = ExtendedGLSLPreprocessor.ShaderType.COMPUTE;
        ProgramAssemblyInfo assemblyInfo = new ProgramAssemblyInfo();
        return glslPreprocessor.preprocess(shaderCode, type, assemblyInfo);
    }

    private String getShaderSource()  {
        URL resource = getClass().getClassLoader().getResource(SHADER_LOCATION);
        try {
            return IOUtils.toString(resource, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int genBoundingBox(SceneMesh mesh) {
        return -1;
    }
}
