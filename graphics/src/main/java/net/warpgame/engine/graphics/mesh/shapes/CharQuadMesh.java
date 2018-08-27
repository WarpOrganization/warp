package net.warpgame.engine.graphics.mesh.shapes;

public class CharQuadMesh extends CustomQuadMesh {
    private static final float[] VERTICES = new float[]{
            0f, -1.0f,
            1.0f, -1.0f,
            1.0f, 0f,
            0f, 0f
    };

    public CharQuadMesh(){
        super(VERTICES);
    }
}
