package net.warpgame.engine.graphics.mesh.shapes;

import net.warpgame.engine.graphics.mesh.VAOMesh;
import org.lwjgl.opengl.GL11;

public class CustomQuadMesh extends VAOMesh {

    private static final int[] INDICES = new int[]{
            0, 1, 2, 0, 2, 3
    };

    public CustomQuadMesh(float[] vertices){
        super(new float[][]{vertices}, new int[]{2}, new int[]{GL11.GL_FLOAT}, vertices.length/2, INDICES);
    }
}
