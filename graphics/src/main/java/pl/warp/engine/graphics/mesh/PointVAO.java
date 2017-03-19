package pl.warp.engine.graphics.mesh;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.graphics.program.rendering.particle.dot.DotParticleProgram;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 23
 *
 */
public class PointVAO { // "Or the overkill definition"

    private int positionVBO;
    private int indexBuff;
    private int vao;

    public PointVAO(Vector3f offset) {
        initBuffers(offset);
        createVao();
    }

    private void initBuffers(Vector3f offset) {
        this.positionVBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[]{offset.x, offset.y, offset.z}, GL15.GL_STATIC_DRAW);
        this.indexBuff = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, new int[]{0}, GL15.GL_STATIC_DRAW);
    }

    private void createVao() {
        this.vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        GL20.glEnableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVBO);
        GL20.glVertexAttribPointer(DotParticleProgram.POSITION_ATTR, 3, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuff);
        GL30.glBindVertexArray(0);
    }

    public void draw() {
        GL30.glBindVertexArray(vao);
        GL11.glDrawElements(GL11.GL_POINTS, 1, GL11.GL_UNSIGNED_INT, 0);
        GL30.glBindVertexArray(0);
    }
}
