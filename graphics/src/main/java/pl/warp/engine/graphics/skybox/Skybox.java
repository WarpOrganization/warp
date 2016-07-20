package pl.warp.engine.graphics.skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.shader.program.cubemap.CubemapProgram;
import pl.warp.engine.graphics.utility.BufferTools;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 20
 */
public class Skybox {

    private static float[] vertices = {
            -1, 1, -1, 1.0f,
            -1, -1, -1, 1.0f,
            1, -1, -1, 1.0f,
            1, 1, -1, 1.0f,

            1, 1, -1, 1.0f,
            1, -1, -1, 1.0f,
            1, -1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, 1, 1, 1.0f,
            -1, -1, 1, 1.0f,
            1, -1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, 1, -1, 1.0f,
            -1, -1, -1, 1.0f,
            -1, 1, 1, 1.0f,
            -1, -1, 1, 1.0f,

            -1, 1, -1, 1.0f,
            1, 1, -1, 1.0f,
            -1, 1, 1, 1.0f,
            1, 1, 1, 1.0f,

            -1, -1, -1, 1.0f,
            1, -1, -1, 1.0f,
            -1, -1, 1, 1.0f,
            1, -1, 1, 1.0f
    };

    private static int[] indices = new int[]{
            2, 1, 0,
            3, 2, 0,

            6, 5, 4,
            7, 6, 4,

            9, 10, 11,
            8, 9, 11,

            13, 15, 14,
            12, 13, 14,

            17, 16, 18,
            19, 17, 18,

            21, 23, 22,
            20, 21, 22
    };

    private static final int iAmount = indices.length;

    private int vao;

    public Skybox() {
        this.vao = createSkybox();
    }

    public void render() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glCullFace(GL11.GL_FRONT);
        GL30.glBindVertexArray(vao);
        GL11.glDrawElements(GL11.GL_TRIANGLES, iAmount, GL11.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glCullFace(GL11.GL_BACK);
    }

    private static int createSkybox() {
        int vBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vBuff);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferTools.toDirectBuffer(vertices), GL15.GL_STATIC_DRAW);

        int iBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferTools.toDirectBuffer(indices), GL15.GL_STATIC_DRAW);

        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL20.glEnableVertexAttribArray(CubemapProgram.ATTR_VERTEX);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vBuff);
        GL20.glVertexAttribPointer(CubemapProgram.ATTR_VERTEX, 4, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuff);

        GL30.glBindVertexArray(0);
        return vao;
    }

    public void delete() {
        GL30.glDeleteVertexArrays(vao);
    }
}
