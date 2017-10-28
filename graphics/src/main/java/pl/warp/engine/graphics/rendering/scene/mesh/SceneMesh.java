package pl.warp.engine.graphics.rendering.scene.mesh;

import pl.warp.engine.graphics.mesh.VAOMesh;

/**
 * @author Jaca777
 * Created 2017-10-01 at 00
 */
public class SceneMesh extends VAOMesh {
    public static final int[] SIZES = {3, 2, 3};

    public SceneMesh(float[] vertices, float[] texCoords, float[] normals, int[] indices) {
        super(new float[][] {vertices, texCoords, normals}, SIZES, vertices.length, indices);
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
