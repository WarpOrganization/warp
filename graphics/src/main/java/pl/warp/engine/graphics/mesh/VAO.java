package pl.warp.engine.graphics.mesh;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 *         Created 2016-07-29 at 17
 */
public class VAO {
    private int vao;
    private int[] buffers;
    private int indexbuffer;

    public VAO(int vao, int[] buffers, int indexbuffer) {
        this.vao = vao;
        this.buffers = buffers;
        this.indexbuffer = indexbuffer;
    }

    public VAO(int[] buffers, int indexbuffer, int[] sizes, int[] types) {
        this.vao = GL30.glGenVertexArrays();
        this.buffers = buffers;
        this.indexbuffer = indexbuffer;
        createVAO(buffers, indexbuffer, sizes, types);
    }

    private void createVAO(int[] buffers, int indexbuffer, int[] sizes, int[] types) {
        GL30.glBindVertexArray(vao);
        for (int i = 0; i < buffers.length; i++) {
            GL20.glEnableVertexAttribArray(i);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buffers[i]);
            GL20.glVertexAttribPointer(i, sizes[i], types[i], false, 0, 0);
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexbuffer);
        GL30.glBindVertexArray(0);
    }

    public void bind() {
        GL30.glBindVertexArray(vao);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void destroyExceptIndex() {
        GL30.glDeleteVertexArrays(vao);
        for(int buffer : buffers)
            GL15.glDeleteBuffers(buffer);
    }

    public void destroy() {
        GL30.glDeleteVertexArrays(vao);
        for(int buffer : buffers)
            GL15.glDeleteBuffers(buffer);
        GL15.glDeleteBuffers(indexbuffer);
    }

}
