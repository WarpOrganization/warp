package pl.warp.engine.graphics.rendering.scene.mesh;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.graphics.mesh.VAOMesh;

/**
 * @author Jaca777
 * Created 2017-10-01 at 00
 */
public class SceneMesh extends VAOMesh {
    public static final int[] SIZES = {3, 2, 3};
    private static final int[] TYPES = {GL11.GL_FLOAT, GL11.GL_FLOAT, GL11.GL_FLOAT};

    public SceneMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        super(new float[][] {vertices, texCoords, normals}, SIZES, TYPES, vertices.length, indices);
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
