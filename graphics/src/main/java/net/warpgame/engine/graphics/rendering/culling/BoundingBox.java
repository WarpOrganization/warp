package net.warpgame.engine.graphics.rendering.culling;

import net.warpgame.engine.graphics.rendering.scene.mesh.SceneMesh;
import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2017-10-28 at 01
 */
public class BoundingBox extends SceneMesh {

    private static final int INDICES[] = {
            2, 4, 1, 8, 6, 5, 5, 2, 1, 6, 3, 2, 3, 8, 4, 1, 8, 5,
            2, 3, 4, 8, 7, 6, 5, 6, 2, 6, 7, 3, 3, 7, 8, 1, 4, 8,
    };

    public BoundingBox(Vector3f max, Vector3f min) {
        super(getData(min, max)[0], getData(min, max)[1], getData(min, max)[2], INDICES);
    }

    private static float[][] getData(Vector3f max, Vector3f min) {
        return new float[][] {
                {
                        max.x, min.y, min.z,
                        max.x, min.y, max.z,
                        min.x, min.y, max.z,
                        min.x, min.y, min.z,
                        max.x, max.y, min.z,
                        max.x, max.y, max.z,
                        min.x, max.y, max.z,
                        min.x, max.y, min.z
                },
                {
                        1f, 1f,
                        1f, 0f,
                        0f, 0f,
                        0f, 1f,
                        1f, 1f,
                        1f, 0f,
                        0f, 1f,
                        0f, 0f,
                },
                {
                    (max.x-min.x)/2,  min.y,            (max.z-min.z)/2,
                    (max.x-min.x)/2,  1f,               (max.z-min.z)/2,
                    1f,               (max.y-min.y)/2,  (max.z-min.z)/2,
                    (max.x-min.x)/2,  (max.y-min.y)/2,  1f,
                    min.x,            (max.y-min.y)/2,  (max.z-min.z)/2,
                    (max.x-min.x)/2,  (max.y-min.y)/2,  min.z,
                    (max.x-min.x)/2,  min.y,            (max.z-min.z)/2,
                    (max.x-min.x)/2,  1f,               (max.z-min.z)/2,
                }
        };
    }
}
