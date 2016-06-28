package pl.warp.engine.graphics.shader;

import com.google.common.io.CharStreams;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import pl.warp.engine.graphics.texture.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 12
 */
public abstract class Program {

    private final int program;

    public Program(InputStream vertexShader, InputStream fragmentShader, String[] outNames) {
        try {
            int vS = ShaderUtil.compileShader(GL20.GL_VERTEX_SHADER, CharStreams.toString(new InputStreamReader(vertexShader)));
            int fS = ShaderUtil.compileShader(GL20.GL_FRAGMENT_SHADER, CharStreams.toString(new InputStreamReader(fragmentShader)));
            this.program = ShaderUtil.createProgram(vS, fS, outNames);
            GL20.glUseProgram(this.program);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Program(String vertexSource, String fragmentSource, String[] outNames) {
        int vS = ShaderUtil.compileShader(GL20.GL_VERTEX_SHADER, vertexSource);
        int fS = ShaderUtil.compileShader(GL20.GL_FRAGMENT_SHADER, fragmentSource);
        this.program = ShaderUtil.createProgram(vS, fS, outNames);
        GL20.glUseProgram(this.program);
    }

    public Program(int program) {
        this.program = program;
        GL20.glUseProgram(this.program);
    }

    /**
     * Binds the program.
     */
    public void use() {
        GL20.glUseProgram(this.program);
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

    protected void setOutput(String[] outNames) {
        for (int i = 0; i < outNames.length; i++) {
            String name = outNames[i];
            GL30.glBindFragDataLocation(program, i, name);
        }
    }


    /**
     * @param name A Name of an attribute to search for.
     * @return Location of the attribute with the given name.
     */
    public int getAttributeLocation(String name) {
        return GL20.glGetAttribLocation(program, name);
    }

    /**
     * @param name A Name of an uniform to search for.
     * @return Location of the uniform with the given name.
     */
    public int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(program, name);
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

    public void setUniformf(int location, float i) {
        GL20.glUniform1f(location, i);
    }

    public void setUniformi(int location, int i) {
        GL20.glUniform1i(location, i);
    }

    public void setUniformV4(int location, float v1, float v2, float v3, float v4) {
        GL20.glUniform4f(location, v1, v2, v3, v4);
    }

    public void setUniformV2(int location, float v1, float v2) {
        GL20.glUniform2f(location, v1, v2);
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
        return program;
    }
}
