package pl.warp.engine.graphics.mesh;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.utility.BufferTools;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

/**
 * @author Jaca777
 *         Created 2015-06-25 at 14
 */
public class Quad {

    private static final float[] VERTICES = new float[]{
            -1.0f, -1.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            -1.0f, 1.0f, 0.0f, 1.0f
    };

    private static final int[] INDICES = new int[]{
            0, 1, 2, 0, 2, 3
    };

    public static final int INDICES_NUMBER = INDICES.length;

    private VAO vao;

    public Quad() {

        int vertexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferTools.toDirectBuffer(VERTICES), GL15.GL_STATIC_DRAW);


        int indexBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferTools.toDirectBuffer(INDICES), GL15.GL_STATIC_DRAW);

        this.vao = new VAO(new int[]{vertexBuffer}, indexBuffer, new int[]{4}, new int[]{GL_FLOAT});
    }

    public void bind() {
        vao.bind();
    }

    public void draw() {
        GL11.glDrawElements(GL11.GL_TRIANGLES, Quad.INDICES_NUMBER, GL11.GL_UNSIGNED_INT, 0);
    }

    public void destroy() {
        vao.destroy();
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }
}
