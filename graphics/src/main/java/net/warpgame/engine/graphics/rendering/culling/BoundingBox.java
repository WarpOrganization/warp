package net.warpgame.engine.graphics.rendering.culling;

import org.joml.Vector3f;
import net.warpgame.engine.graphics.mesh.IndexedMesh;

/**
 * @author Jaca777
 * Created 2017-10-28 at 01
 */
public class BoundingBox extends IndexedMesh {

    private static final int INDICES[] = {
            0, 1, 2, 3, 7, 1, 5, 4, 7, 6, 2, 4, 0, 1
    };


    public BoundingBox(Vector3f max, Vector3f min) {
        super(getData(max, min), new int[]{3}, 8, INDICES);
    }

    private static float[][] getData(Vector3f max, Vector3f min) {
        return new float[][]{
                {
                        min.x, min.y, max.z,
                        max.x, min.y, max.z,
                        min.x, max.y, max.z,
                        max.x, max.y, max.z,
                        min.x, min.y, min.z,
                        max.x, min.y, min.z,
                        min.x, max.y, min.z,
                        max.x, max.y, min.z
                }
        };
    }
}
