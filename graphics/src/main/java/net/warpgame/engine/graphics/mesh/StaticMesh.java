package net.warpgame.engine.graphics.mesh;

import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2017-10-01 at 00
 */
public class StaticMesh extends VAOMesh {
    public static final int[] SIZES = {3, 2, 3};
    private static final int[] TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};

    public StaticMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        super(new float[][] {vertices, texCoords, normals}, SIZES, TYPES, vertices.length/3, indices);
    }

    public int getVertexBuff() {
        return getBuffer(0);
    }

    public int getTexCoordBuff() {
        return getBuffer(1);
    }

    public int getNormalBuff() {
        return getBuffer(2);
    }
}
