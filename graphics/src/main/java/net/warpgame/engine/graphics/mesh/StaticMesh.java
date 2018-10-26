package net.warpgame.engine.graphics.mesh;

import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2017-10-01 at 00
 */
public class StaticMesh {
    public static final int[] SIZES = {3, 2, 3};
    private static final int[] TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};

    public StaticMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
