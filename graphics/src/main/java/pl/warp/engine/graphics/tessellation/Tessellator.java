package pl.warp.engine.graphics.tessellation;

import org.lwjgl.opengl.GL15;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.mesh.IndexedMesh;
import pl.warp.engine.graphics.tessellation.program.TessellationProgram;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */

@Service
public class Tessellator {

    public enum TessellatorMode {
        BEZIER, FLAT
    }

    public static final String BEZIER_TESSELLATOR_LOCATION = "tessellation/bezier";
    public static final String FLAT_TESSELLATOR_LOCATION = "tessellation/flat";

    private TessellationProgram bezierTessellationProgram;
    private TessellationProgram flatTesselationProgram;

    public void init() {
        this.bezierTessellationProgram = new TessellationProgram(BEZIER_TESSELLATOR_LOCATION);
        this.flatTesselationProgram = new TessellationProgram(FLAT_TESSELLATOR_LOCATION);
    }

    public void setOutput(int outputBuffer) {

    }

    public IndexedMesh tessellate(IndexedMesh mesh, TessellatorMode mode, float tessellationLevel) {
        TessellationProgram program = null;
        switch (mode) {
            case FLAT:
                program = flatTesselationProgram;
                break;
            case BEZIER:
                program = bezierTessellationProgram;
                break;
        }
        program.use();
        program.setTessellationLevel(tessellationLevel);

        int vertices = GL15.glGenBuffers();
        int textureCoords = GL15.glGenBuffers();
        int normals = GL15.glGenBuffers();

        //todo tessellation

        return null;
    }

    public int getVerticesFactor(int level) {
        int triangles = level / 2;
        int maxSubtriangles = (level - 1) * 2;
        int extraTriangles = ((triangles - 1) * triangles) * 2;
        int innerTriangle = (level % 2);
        return (maxSubtriangles * triangles - extraTriangles) * 3 + innerTriangle;
    }


}
