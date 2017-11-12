package pl.warp.engine.graphics.rendering.scene.tesselation;

import pl.warp.engine.graphics.program.ProgramAssemblyInfo;
import pl.warp.engine.graphics.rendering.scene.program.SceneRenderingProgram;

/**
 * @author Jaca777
 * Created 2017-09-25 at 17
 */
public class NoTessellationRenderingProgram extends SceneRenderingProgram {
    public NoTessellationRenderingProgram() {
        super(new ProgramAssemblyInfo("scene"));
    }
}
