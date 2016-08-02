package pl.warp.engine.graphics.mesh;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 15.01.15 at 15:28
 */
public class VAOMesh extends Mesh {

    protected VAO vao;

    /**
     * @see Mesh#Mesh(float[], float[], float[], int[])
     */
    public VAOMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        super(vertices, texCoords, normals, indices);
        this.vao = createVAO();
    }

    public VAOMesh(int indices) {
        super(indices);
        this.vao = new VAO(new int[]{vertexBuff, normalBuff, texCoordBuff}, indexBuff, VAO_SIZES, VAO_TYPES);
    }

    /**
     * @see Mesh#Mesh(FloatBuffer, FloatBuffer, FloatBuffer, IntBuffer, int)
     */
    public VAOMesh(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int numElements) {
        super(vertices, texCoords, normals, indices, numElements);
        this.vao = createVAO();
    }

    /**
     * Binds vao and draws mesh.
     */

    @Override
    public void bind() {
        vao.bind();
    }

    @Override
    public void finalizeRendering() {
        GL30.glBindVertexArray(0);
    }

    private static final int[] VAO_SIZES = {3, 2, 3};
    private static final int[] VAO_TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};

    protected VAO createVAO() {
        return new VAO(new int[]{this.vertexBuff, this.texCoordBuff, this.normalBuff}, indexBuff, VAO_SIZES, VAO_TYPES);
    }

    @Override
    public void unload() {
        this.vao.destroy();
    }
}
