package pl.warp.engine.graphics.shader;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import pl.warp.engine.graphics.shader.extendedglsl.ConstantField;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgram;
import pl.warp.engine.graphics.shader.extendedglsl.ExtendedGLSLProgramCompiler;
import pl.warp.engine.graphics.shader.extendedglsl.LocalProgramLoader;
import pl.warp.engine.graphics.texture.Texture;

import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public abstract class Program {

    protected ExtendedGLSLProgram program;

    public Program(String vertexShaderName, String fragmentShaderName, ExtendedGLSLProgramCompiler compiler) {
        this.program = compiler.compile(vertexShaderName, fragmentShaderName);
        GL20.glUseProgram(this.program.getGLProgram());
    }

    public Program(int program, int vertexShader, int fragmentShader) {
        this.program = new ExtendedGLSLProgram(fragmentShader, vertexShader, program);
    }

    public Program(String programName) {
        this(programName + "/vert", programName + "/frag",
                new ExtendedGLSLProgramCompiler(ConstantField.EMPTY_CONSTANT_FIELD, LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER));
    }


    protected Program() {
    }


    /**
     * Binds the program.
     */
    public void use() {
        GL20.glUseProgram(this.program.getGLProgram());
    }

    /**
     * Binds the texture to the given sampler.
     *
     * @param texture Texture to bind.
     * @param sampler Sampler offset to bind the texture to.
     */
    public void useTexture(Texture texture, int sampler) {
        useTexture(texture.getTexture(), texture.getType(), sampler);
    }

    public void useTexture(int texture, int tType, int sampler) {
        if (texture != -1) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
            GL11.glBindTexture(tType, texture);
        }
    }

    public void delete() {
        GL20.glDetachShader(program.getGLProgram(), program.getVertexShader());
        GL20.glDetachShader(program.getGLProgram(), program.getFragmentShader());
        GL20.glDeleteProgram(program.getGLProgram());
        GL20.glDeleteShader(program.getVertexShader());
        GL20.glDeleteShader(program.getFragmentShader());
    }


    /**
     * @param name A Name of an attribute to search for.
     * @return Location of the attribute with the given name.
     */
    public int getAttributeLocation(String name) {
        return GL20.glGetAttribLocation(program.getGLProgram(), name);
    }

    /**
     * @param name A Name of an uniform to search for.
     * @return Location of the uniform with the given name.
     */
    public int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(program.getGLProgram(), name);
    }

    public void setUniformMatrix4(int location, FloatBuffer matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix);
    }

    private FloatBuffer tempBuff4 = BufferUtils.createFloatBuffer(16);

    public void setUniformMatrix4(int location, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix.get(tempBuff4));
    }

    public void setUniformMatrix3(int location, FloatBuffer matrix) {
        GL20.glUniformMatrix3fv(location, false, matrix);
    }

    private FloatBuffer tempBuff3 = BufferUtils.createFloatBuffer(9);

    public void setUniformMatrix3(int location, Matrix3f matrix) {
        GL20.glUniformMatrix3fv(location, false, matrix.get(tempBuff3));
    }

    public void setUniformf(int location, float f) {
        GL20.glUniform1f(location, f);
    }

    public void setUniformi(int location, int i) {
        GL20.glUniform1i(location, i);
    }

    public void setUniformb(int location, boolean b) {
        GL20.glUniform1i(location, b ? 1 : 0);
    }

    public void setUniformV4(int location, float v1, float v2, float v3, float v4) {
        GL20.glUniform4f(location, v1, v2, v3, v4);
    }

    public void setUniformV2(int location, float v1, float v2) {
        GL20.glUniform2f(location, v1, v2);
    }

    public void setUniformV2(int location, Vector2f vector) {
        setUniformV2(location, vector.x, vector.y);
    }

    public void setUniformV3(int location, float v1, float v2, float v3) {
        GL20.glUniform3f(location, v1, v2, v3);
    }

    public void setUniformV4(int location, Vector4f vector) {
        GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    public void setUniformV3(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public int getProgram() {
        return program.getGLProgram();
    }

    protected void setTextureLocation(String name, int sampler) {
        setUniformi(getUniformLocation(name), sampler);
    }
}
