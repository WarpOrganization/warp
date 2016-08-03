package pl.warp.engine.graphics.mesh;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import pl.warp.engine.graphics.utility.BufferTools;


import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

/**
 * @author Jaca777
 *         Created 21.12.14 at 18:51
 */
public class Mesh {
    public static final int INDEX_ATTRIBUTE = 0;
    public static final int TEX_COORD_ATTRIBUTE = 1;
    public static final int NORMAL_ATTRIBUTE = 2;

    protected int vertexBuff = -1,
            texCoordBuff = -1,
            normalBuff = -1,
            indexBuff = -1;
    protected int indices = -1,
            vertices = -1;

    /**
     * @param verticesNum Buffer containing vertices
     * @param texCoords   Buffer containing texture coordinates
     * @param normals     Buffer containing normals
     * @param indicesNum  Buffer containing indices
     * @param indicesNum  Number of elements
     */
    public Mesh(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int indicesNum, int verticesNum) {
        loadBuffers(vertices, texCoords, normals, indices);
        this.indices = indicesNum;
        this.vertices = verticesNum;

    }

    public Mesh(int indices, int vertices) {
        this(GL15.glGenBuffers(), GL15.glGenBuffers(), GL15.glGenBuffers(), GL15.glGenBuffers(), indices, vertices);
    }

    protected void loadBuffers(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices) {
        Objects.requireNonNull(vertices, "Vertex buffer can't be null");
        Objects.requireNonNull(indices, "Index buffer can't be null");

        this.vertexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuff);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        if (texCoords != null) {
            this.texCoordBuff = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordBuff);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoords, GL15.GL_STATIC_DRAW);
        }
        if (normals != null) {
            this.normalBuff = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBuff);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normals, GL15.GL_STATIC_DRAW);
        }
        this.indexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    /**
     * @param vertices  Array consisting of vertices
     * @param texCoords Array consisting of texture coordinates.
     * @param normals   Array consisting of normals
     * @param indices   Array consisting of indices
     */
    public Mesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        this(BufferTools.toDirectBuffer(vertices),
                BufferTools.toDirectBuffer(texCoords),
                BufferTools.toDirectBuffer(normals),
                BufferTools.toDirectBuffer(indices),
                indices.length, vertices.length / 3);
    }

    /**
     * @param vertexBuff   Vertex buffer name.
     * @param texCoordBuff Texture coordinate buffer name.
     * @param normalBuff   Normal buffer name.
     * @param indexBuff    Index buffer name.
     * @param indices      Number of elements.
     */
    public Mesh(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int vertices) {
        this.vertexBuff = vertexBuff;
        this.texCoordBuff = texCoordBuff;
        this.normalBuff = normalBuff;
        this.indexBuff = indexBuff;
        this.indices = indices;
        this.vertices = vertices;
    }

    /**
     * @return vertex buffer name
     */
    public int getVertexBuff() {
        return vertexBuff;
    }

    /**
     * @return texture coordinate buffer name
     */
    public int getTexCoordBuff() {
        return texCoordBuff;
    }

    /**
     * @return normal buffer name
     */
    public int getNormalBuff() {
        return normalBuff;
    }

    /**
     * @return index buffer name
     */
    public int getIndexBuff() {
        return indexBuff;
    }


    public int getIndicesAmount() {
        return indices;
    }


    /**
     * Sets index buffer name.
     */
    public void setIndexBuff(int indexBuff) {
        this.indexBuff = indexBuff;
    }

    /**
     * Sets index buffer name.
     */
    public void setNormalBuff(int normalBuff) {
        this.normalBuff = normalBuff;
    }

    /**
     * Sets number of elements.
     */
    public void setIndicesAmount(int indices) {
        this.indices = indices;
    }

    /**
     * Sets texture coordinate buffer name.
     */
    public void setTexCoordBuff(int texCoordBuff) {
        this.texCoordBuff = texCoordBuff;
    }

    /**
     * Sets vertex buffer name.
     */
    public void setVertexBuff(int vertexBuff) {
        this.vertexBuff = vertexBuff;
    }

    /**
     * Binds buffers.
     */
    public void bind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuff);
        GL20.glVertexAttribPointer(INDEX_ATTRIBUTE, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordBuff);
        GL20.glVertexAttribPointer(TEX_COORD_ATTRIBUTE, 2, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(TEX_COORD_ATTRIBUTE);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBuff);
        GL20.glVertexAttribPointer(NORMAL_ATTRIBUTE, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(NORMAL_ATTRIBUTE);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
    }

    public void finalizeRendering() {
        //Do nothing
    }

    /**
     * Calls OpenGL draw function.
     */
    public void render() {
        GL11.glDrawElements(GL11.GL_TRIANGLES, indices, GL11.GL_UNSIGNED_INT, 0);
    }


    /**
     * Binds each vbo and draws mesh.
     */
    public void draw() {
        bind();
        render();
        finalizeRendering();
    }


    /**
     * Unloads vbos.
     */
    public void unload() {
        GL15.glDeleteBuffers(vertexBuff);
        GL15.glDeleteBuffers(texCoordBuff);
        GL15.glDeleteBuffers(normalBuff);
        GL15.glDeleteBuffers(indexBuff);
    }

    private FloatBuffer tempVertexData;

    public synchronized void setVertexData(float[] vertices) {
        if (tempVertexData == null) tempVertexData = BufferUtils.createFloatBuffer(indices * 3);
        tempVertexData.clear();
        FloatBuffer data = tempVertexData.put(vertices);
        setBufferData(vertexBuff, data);
    }

    private FloatBuffer tempTexCoordData;

    public synchronized void setTexCoordData(float[] texCoords) {
        if (tempTexCoordData == null) tempTexCoordData = BufferUtils.createFloatBuffer(indices * 2);
        tempTexCoordData.clear();
        FloatBuffer data = tempTexCoordData.put(texCoords);
        setBufferData(texCoordBuff, data);
    }

    private FloatBuffer tempNormalData;

    public synchronized void setNormalData(float[] normals) {
        if (tempNormalData == null) tempNormalData = BufferUtils.createFloatBuffer(indices * 3);
        tempNormalData.clear();
        FloatBuffer data = tempNormalData.put(normals);
        setBufferData(normalBuff, data);
    }

    private IntBuffer tempIndicesData;

    public synchronized void setIndexData(int[] indices) {
        if (tempIndicesData == null) tempIndicesData = BufferUtils.createIntBuffer(this.indices);
        tempIndicesData.clear();
        IntBuffer data = tempIndicesData.put(indices);
        setBufferData(indexBuff, data);
    }

    private void setBufferData(int buffer, FloatBuffer data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    }

    private void setBufferData(int buffer, IntBuffer data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    }
}
