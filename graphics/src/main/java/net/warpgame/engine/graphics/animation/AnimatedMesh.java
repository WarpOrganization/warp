package net.warpgame.engine.graphics.animation;

import net.warpgame.engine.graphics.mesh.VAOMesh;
import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2018-06-09 at 15
 */
public class AnimatedMesh extends VAOMesh {

    public static final int[] SIZES = {3, 2, 3, 3, 3, 3};
    private static final int[] TYPES = {
            GL11.GL_FLOAT,
            GL11.GL_FLOAT,
            GL11.GL_FLOAT,
            GL11.GL_FLOAT,
            GL11.GL_FLOAT,
            GL11.GL_UNSIGNED_INT
    };

    public AnimatedMesh(
            float[] vertices,
            float[] texCoords,
            float[] normals,
            int[] jointIds,
            float[] jointWeights,
            int[] indices
    ) {
        super(new float[][]{vertices, texCoords, normals, jointWeights},
                new int[][]{jointIds},
                SIZES,
                TYPES,
                vertices.length / 3,
                indices
        );
    }
}
