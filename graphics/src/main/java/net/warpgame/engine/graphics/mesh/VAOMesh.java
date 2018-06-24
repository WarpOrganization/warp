package net.warpgame.engine.graphics.mesh;


import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 * Created 15.01.15 at 15:28
 */
public class VAOMesh extends IndexedMesh {

    protected VAO vao;

    public VAOMesh(float[][] fData, int[] sizes, int types[], int vertices, int[] indices) {
        super(toDirectBuffers(fData), sizes, vertices, indices);
        this.vao = createVAO(sizes, types);
    }

    public VAOMesh(float[][] fData, int[][] iData, int[] sizes, int types[], int vertices, int[] indices) {
        super(ArrayUtils.addAll(toDirectBuffers(fData), toDirectBuffers(iData)), sizes, vertices, indices);
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
