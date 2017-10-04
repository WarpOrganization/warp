package pl.warp.engine.graphics.mesh;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 *         Created 15.01.15 at 15:28
 */
public class VAOMesh extends IndexedMesh {

    protected VAO vao;

    public VAOMesh(float[][] data, int[] sizes, int vertices, int[] indices) {
        super(data, sizes, vertices, indices);
        this.vao = createVAO();
    }

    public VAOMesh(int[] sizes, int indices, int vertices) {
        super(sizes, indices, vertices);
        this.vao = createVAO();
    }

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
        return new VAO(buffers, indexBuff, VAO_SIZES, VAO_TYPES);
    }

    @Override
    public void destroy() {
        this.vao.destroy();
    }


}
