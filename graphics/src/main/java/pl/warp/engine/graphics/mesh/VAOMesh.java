package pl.warp.engine.graphics.mesh;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 *         Created 15.01.15 at 15:28
 */
public class VAOMesh extends Mesh {

    protected int vaoName = -1;

    /**
     * @see Mesh#Mesh(int, int, int, int, int, int[])
     */
    public VAOMesh(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int vao, int[] attributes) {
        super(vertexBuff, texCoordBuff, normalBuff, indexBuff, indices, attributes);
        this.vaoName = vao;
    }

    /**
     * @see Mesh#Mesh(float[], float[], float[], int[], int[])
     */
    public VAOMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices, int[] attributes) {
        super(vertices, texCoords, normals, indices, attributes);
        createVAO();
    }

    /**
     * @see Mesh#Mesh(FloatBuffer, FloatBuffer, FloatBuffer, IntBuffer, int, int[])
     */
    public VAOMesh(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int numElements, int[] attributes) {
        super(vertices, texCoords, normals, indices, numElements, attributes);
        createVAO();
    }

    /**
     * @see Mesh#Mesh(int, int, int, int, int, int[])
     */
    public VAOMesh(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int[] attributes, int vaoName) {
        super(vertexBuff, texCoordBuff, normalBuff, indexBuff, indices, attributes);
        this.vaoName = vaoName;
    }

    /**
     * Binds vao and draws mesh.
     */

    @Override
    public void bind() {
        GL30.glBindVertexArray(vaoName);
    }

    @Override
    public void finalizeRendering() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Creates VAO with vertex, texture coordinate and normal buffer for given attributes locations.
     *
     * @param attributes Array consisting of attributes locations. Organization:
     * 0 - vertex attribute
     * 1 - texture coordinate attribute
     * 2 - normal attribute
     */

    private static final int[] VAO_SIZES = {4, 2, 3};
    private static final int[] VAO_TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};

    protected void createVAO() {
        this.vaoName = createVAO(new int[]{this.vertexBuff, this.texCoordBuff, this.normalBuff}, indexBuff, attributes, VAO_SIZES, VAO_TYPES);
    }

    /**
     * Creates new VAO.
     *
     * @param buffers     Buffers to be added to VAO.
     * @param attributes  Attributes locations.
     * @param indexbuffer Indices.
     * @param sizes       Attributes size.
     * @param types       Attributes types.
     * @return New VAO name.
     */
    public static int createVAO(int[] buffers, int indexbuffer, int[] attributes, int[] sizes, int[] types) {
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        for (int i = 0; i < buffers.length; i++) {
            GL20.glEnableVertexAttribArray(attributes[i]);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffers[i]);
            GL20.glVertexAttribPointer(attributes[i], sizes[i], types[i], false, 0, 0);
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexbuffer);
        GL30.glBindVertexArray(0);
        return vao;
    }


    /**
     * @return VAO name.
     */
    public int getVao() {
        return vaoName;
    }

    @Override
    public void unload() {
        super.unload();
        GL30.glDeleteVertexArrays(vaoName);
    }
}
