package net.warpgame.engine.graphics.rendering.scene.tesselation;

import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.rendering.scene.program.SceneRenderingProgram;

/**
 * @author Jaca777
 * Created 2017-09-25 at 17
 */
public class NoTessellationRenderingProgram extends SceneRenderingProgram {
    public NoTessellationRenderingProgram() {
        super(new ProgramAssemblyInfo("scene"));
    }
}
