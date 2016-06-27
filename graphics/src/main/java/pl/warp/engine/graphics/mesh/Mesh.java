package pl.warp.engine.graphics.mesh;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import pl.warp.engine.graphics.utility.BufferTools;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

/**
 * @author Jaca777
 *         Created 21.12.14 at 18:51
 */
public class Mesh  {
    protected int vertexBuff = -1,
            texCoordBuff = -1,
            normalBuff = -1,
            indexBuff = -1;
    protected int indices = -1;
    protected int[] attributes;

    /**
     * @param vertices Buffer containing vertices
     * @param texCoords Buffer containing texture coordinates
     * @param normals Buffer containing normals
     * @param indices Buffer containing indices
     * @param numElements Number of elements
     * @param attributes Array consisting of the attributes' locations. Organization:
     *      0 - vertex attribute
     *      1 - texture coordinate attribute
     *      2 - normal attribute
     */
    public Mesh(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices, int numElements, int[] attributes) {
        loadBuffers(vertices, texCoords, normals, indices);

        this.indices = numElements;
        this.attributes = attributes;
    }

    protected void loadBuffers(FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, IntBuffer indices){
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
     * @param vertices Array consisting of vertices
     * @param texCoords Array consisting of texture coordinates.
     * @param normals Array consisting of normals
     * @param indices Array consisting of indices
     * @param attributes Array consisting of the attributes' locations. Organization:
     *      0 - vertex attribute
     *      1 - texture coordinate attribute
     *      2 - normal attribute
     */
    public Mesh(float[] vertices, float[] texCoords, float[] normals, int[] indices, int[] attributes) {
        this((vertices == null) ? null : BufferTools.toDirectBuffer(vertices),
                (texCoords == null) ? null : BufferTools.toDirectBuffer(texCoords),
                (normals == null) ? null : BufferTools.toDirectBuffer(normals),
                BufferTools.toDirectBuffer(indices), indices.length, attributes);
    }

    /**
     * @param vertexBuff Vertex buffer name.
     * @param texCoordBuff Texture coordinate buffer name.
     * @param normalBuff Normal buffer name.
     * @param indexBuff Index buffer name.
     * @param indices Number of elements.
     * @param attributes Array consisting of the attributes' locations. Organization:
     *      0 - vertex attribute
     *      1 - texture coordinate attribute
     *      2 - normal attribute
     */
    public Mesh(int vertexBuff, int texCoordBuff, int normalBuff, int indexBuff, int indices, int[] attributes) {
        this.vertexBuff = vertexBuff;
        this.texCoordBuff = texCoordBuff;
        this.normalBuff = normalBuff;
        this.indexBuff = indexBuff;
        this.indices = indices;
        this.attributes = attributes;
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
        GL20.glVertexAttribPointer(attributes[0], 4, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(attributes[0]);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordBuff);
        GL20.glVertexAttribPointer(attributes[1], 2, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(attributes[1]);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBuff);
        GL20.glVertexAttribPointer(attributes[2], 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(attributes[2]);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
    }

    public void finalizeRendering(){
        //Do nothing
    }

    /**
     * Calls OpenGL draw function.
     */
    public void render(){
        GL11.glDrawElements(GL11.GL_TRIANGLES, indices, GL11.GL_UNSIGNED_INT, 0);
    }


    /**
     * Binds each vbo and draws mesh.
     */
    public void draw(){
        bind();
        render();
        finalizeRendering();
    }


    /**
     * Unloads vbos.
     */
    public void unload(){
        GL15.glDeleteBuffers(vertexBuff);
        GL15.glDeleteBuffers(texCoordBuff);
        GL15.glDeleteBuffers(normalBuff);
        GL15.glDeleteBuffers(indexBuff);
    }

}
