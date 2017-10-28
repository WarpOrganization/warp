package pl.warp.engine.graphics.rendering.culling;

import org.apache.commons.io.IOUtils;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.program.GLSLShaderCompiler;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import pl.warp.engine.graphics.program.extendedglsl.preprocessor.ExtendedGLSLPreprocessor;
import pl.warp.engine.graphics.rendering.scene.mesh.SceneMesh;
import pl.warp.engine.graphics.utility.BufferTools;

import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

/**
 * @author Jaca777
 * Created 2017-09-30 at 23
 */


@Service
public class BoundingBoxCalculator {

    private static final String SHADER_LOCATION = "bbox/firstpass.glsl";
    private static final int LOCAL_SIZE = 32;
    private static final int INVOCATION_SIZE = 32;
    private static final int INITIAL_SSBO_SIZE = 3 * 4 * 64 * 1024; //768 KB, 64 * 2^10 verts
    private static final int BLOCK_INDEX = 3;

    private int program;
    private int ssbo;

    public void initialize() {
        compileProgram();
        loadBuffer();
    }

    private void loadBuffer() {
        this.ssbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
        GL15.glBufferData(GL43.GL_SHADER_STORAGE_BUFFER, INITIAL_SSBO_SIZE, GL15.GL_DYNAMIC_COPY);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, 0);
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

    private Vector3f tempMin = new Vector3f();
    private Vector3f tempMax = new Vector3f();
    public BoundingBox genBoundingBox(SceneMesh mesh) {
        prepare(mesh);
        compute(mesh);
        loadResults();
        return new BoundingBox(tempMax, tempMin);
    }


    protected void prepare(SceneMesh mesh) {
        GL20.glUseProgram(program);
        int verts = mesh.getVertexCount();
        int vertBuff = mesh.getVertexBuff();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertBuff);
        GL15.glBindBuffer(GL43.GL_SHADER_STORAGE_BUFFER, ssbo);
        GL31.glCopyBufferSubData(GL15.GL_ARRAY_BUFFER, GL43.GL_SHADER_STORAGE_BUFFER, 0, 0, verts * 3 * 4);
        GL30.glBindBufferBase(GL43.GL_SHADER_STORAGE_BUFFER, BLOCK_INDEX, ssbo);
        GL43.glShaderStorageBlockBinding(program, BLOCK_INDEX, BLOCK_INDEX);

    }

    protected void compute(SceneMesh mesh) {
        int groupsX = mesh.getVertexCount() / (LOCAL_SIZE * INVOCATION_SIZE);
        GL43.glDispatchCompute(groupsX, 3, 1);
    }

    private FloatBuffer tempBuff = BufferTools.createFloatBuffer(6);
    private void loadResults() {
        GL15.glGetBufferSubData(GL43.GL_SHADER_STORAGE_BUFFER, 0, tempBuff);
        tempMax.set(tempBuff);
        tempBuff.position(tempBuff.position() + 3);
        tempMin.set(tempBuff);
        tempBuff.rewind();
    }
}
