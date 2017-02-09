package pl.warp.engine.graphics.mesh.shapes;

import pl.warp.engine.graphics.mesh.VAOMesh;

/**
 * @author Jaca777
 *         Created 2017-02-09 at 01
 */
public class QuadMesh extends VAOMesh {

    private static final float[] VERTICES = new float[]{
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f
    };

    private static final float[] TEX_COORDS = new float[]{
            1.0f, 0.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    };

    private static final float[] NORMALS = new float[]{
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f
    };

    private static final int[] INDICES = new int[]{
            0, 1, 2, 0, 2, 3
    };

    public QuadMesh() {
        super(VERTICES, TEX_COORDS, NORMALS, INDICES);
    }
}
