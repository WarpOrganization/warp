package pl.warp.engine.graphics.rendering.scene.program;

import pl.warp.engine.graphics.program.ProgramAssemblyInfo;

/**
 * @author Jaca777
 * Created 2017-09-25 at 16
 */
public class FlatTessellationRenderingProgram extends TessellationRenderingProgram {

    public FlatTessellationRenderingProgram() {
        super(ProgramAssemblyInfo
                .withTesselation()
                .setTcsProgramLocation("tcs_flat")
                .setTesProgramLocation("tes_flat")
        );
    }
}
