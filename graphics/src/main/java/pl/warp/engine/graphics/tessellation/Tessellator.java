package pl.warp.engine.graphics.tessellation;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.tessellation.program.TessellationProgram;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */

@Service
public class Tessellator {

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

    public void tessellate(Mesh mesh, TessellatorMode mode, float tessellationLevel) {
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
    }


    public enum TessellatorMode {
        BEZIER, FLAT
    }

}
