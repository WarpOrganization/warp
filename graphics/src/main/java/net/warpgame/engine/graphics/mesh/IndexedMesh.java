package net.warpgame.engine.graphics.mesh;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import net.warpgame.engine.graphics.utility.BufferTools;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Jaca777
 * Created 21.12.14 at 18:51
 */
public class IndexedMesh {

    protected int[] buffers;
    protected int[] sizes;
    protected int indexBuff = -1;
    protected int indices = -1;
    protected int vertices = -1;

    public IndexedMesh(FloatBuffer[] data, int[] sizes, IntBuffer indices, int indicesCount, int verticesCount) {
        loadBuffers(data, indices);
        this.sizes = sizes;
        this.indices = indicesCount;
        this.vertices = verticesCount;

    }

    public IndexedMesh(int[] sizes, int indices, int vertices) {
        this(createBuffers(sizes.length), sizes, GL15.glGenBuffers(), indices, vertices);
    }

    private static int[] createBuffers(int bufferCount) {
        int[] buffers = new int[bufferCount];
        for (int i = 0; i < buffers.length; i++) {
            buffers[i] = GL15.glGenBuffers();
        }
        return buffers;
    }

    protected void loadBuffers(FloatBuffer[] data, IntBuffer indices) {
        this.buffers = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            FloatBuffer buffer = data[i];
            int bufferName = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferName);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
            this.buffers[i] = bufferName;
        }

        this.indexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    public IndexedMesh(float[][] data, int[] sizes, int vertices, int[] indices) {
        this(toDirectBuffers(data),
                sizes,
                BufferTools.toDirectBuffer(indices),
                indices.length, vertices);
    }

    private static FloatBuffer[] toDirectBuffers(float[][] data) {
        FloatBuffer[] buffers = new FloatBuffer[data.length];
        for (int i = 0; i < buffers.length; i++) {
            buffers[i] = BufferTools.toDirectBuffer(data[i]);
        }
        return buffers;
    }

    public IndexedMesh(int[] buffers, int[] sizes, int indexBuff, int indices, int vertices) {
        this.buffers = buffers;
        this.sizes = sizes;
        this.indexBuff = indexBuff;
        this.indices = indices;
        this.vertices = vertices;
    }


    public int getBuffer(int i) {
        return buffers[i];
    }

    public int getBuffersCount() {
        return buffers.length;
    }

    /**
     * Binds buffers.
     */
    public void bind() {

        for (int i = 0; i < buffers.length; i++) {
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffers[i]);
            GL20.glVertexAttribPointer(i, sizes[i], GL11.GL_FLOAT, false, 0, 0);
            GL20.glEnableVertexAttribArray(i);
        }

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

    public void renderPatched() {
        GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, 3);
        GL11.glDrawElements(GL40.GL_PATCHES, indices, GL11.GL_UNSIGNED_INT, 0);
    }

    public void renderInstanced(int instances) {
        GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, indices, GL11.GL_UNSIGNED_INT, 0, instances);
    }

    /**
     * Binds each vbo and draws mesh.
     */
    public void draw() {
        bind();
        render();
        finalizeRendering();
    }

    public void drawPatched() {
        bind();
        renderPatched();
        finalizeRendering();
    }

    /**
     * Binds each vbo and draws mesh instances.
     *
     * @param instances Number of mesh instances
     */
    public void drawInstanced(int instances) {
        bind();
        renderInstanced(instances);
        finalizeRendering();
    }


    /**
     * Unloads vbos.
     */
    public void destroy() {
        for (int buffer : buffers) {
            GL15.glDeleteBuffers(buffer);
        }
        GL15.glDeleteBuffers(indexBuff);
    }


    private FloatBuffer tempData;

    public synchronized void setData(int buffer, float[] vertices) {
        if (tempData == null || tempData.capacity() < this.vertices * 3)
            tempData = BufferUtils.createFloatBuffer(this.vertices * 3);
        tempData.clear();
        FloatBuffer data = tempData.put(vertices);
        data.rewind();
        setBufferData(buffers[buffer], data);
    }


    private IntBuffer tempIndicesData;

    public synchronized void setIndexData(int[] indices) {
        if (tempIndicesData == null || tempIndicesData.capacity() < this.indices)
            tempIndicesData = BufferUtils.createIntBuffer(this.indices);
        tempIndicesData.clear();
        IntBuffer data = tempIndicesData.put(indices);
        data.rewind();
        setBufferData(indexBuff, data);
    }

    public synchronized void setIndexData(IntBuffer indices) {
        setBufferData(indexBuff, indices);
    }

    private void setBufferData(int bufferName, FloatBuffer data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferName);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    }

    private void setBufferData(int bufferName, IntBuffer data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferName);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    }

    public int getIndexCount() {
        return indices;
    }

    public int getVertexCount() {
        return vertices;
    }
}
