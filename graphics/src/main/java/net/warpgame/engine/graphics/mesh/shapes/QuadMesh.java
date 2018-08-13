package net.warpgame.engine.graphics.mesh.shapes;

import net.warpgame.engine.graphics.mesh.VAOMesh;
import org.lwjgl.opengl.GL11;

public class QuadMesh extends VAOMesh {
    private static final float[] VERTICES = new float[]{
            -1.0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f
    };

    private static final int[] INDICES = new int[]{
            0, 1, 2, 0, 2, 3
    };

    public QuadMesh(){
        super(new float[][]{VERTICES}, new int[]{2}, new int[]{GL11.GL_FLOAT}, VERTICES.length/2, INDICES);
    }
}
