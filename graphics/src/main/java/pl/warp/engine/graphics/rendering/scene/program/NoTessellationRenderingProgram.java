package pl.warp.engine.graphics.rendering.scene.program;

import pl.warp.engine.graphics.program.ProgramAssemblyInfo;

/**
 * @author Jaca777
 * Created 2017-09-25 at 17
 */
public class NoTessellationRenderingProgram extends SceneRenderingProgram {
    public NoTessellationRenderingProgram() {
        super(ProgramAssemblyInfo.minimal());
    }
}
