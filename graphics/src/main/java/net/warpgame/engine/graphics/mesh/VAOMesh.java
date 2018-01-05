package net.warpgame.engine.graphics.mesh;


import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 * Created 15.01.15 at 15:28
 */
public class VAOMesh extends IndexedMesh {

    protected VAO vao;

    public VAOMesh(float[][] data, int[] sizes, int types[], int vertices, int[] indices) {
        super(data, sizes, vertices, indices);
        this.vao = createVAO(sizes, types);
    }

    public VAOMesh(int[] sizes, int[] types, int indices, int vertices) {
        super(sizes, indices, vertices);
        this.vao = createVAO(sizes, types);
    }

    @Override
    public void bind() {
        vao.bind();
    }

    @Override
    public void finalizeRendering() {
        GL30.glBindVertexArray(0);
    }

    protected VAO createVAO(int[] sizes, int[] types) {
        return new VAO(buffers, indexBuff, sizes, types);
    }

    @Override
    public void destroy() {
        this.vao.destroy();
    }


}
