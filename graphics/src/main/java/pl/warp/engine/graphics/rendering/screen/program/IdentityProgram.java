package pl.warp.engine.graphics.rendering.screen.program;

import pl.warp.engine.graphics.program.Program;
import pl.warp.engine.graphics.program.ProgramAssemblyInfo;

/**
 * @author Jaca777
 * Created 2017-11-11 at 16
 */
public class IdentityProgram extends Program{
    public IdentityProgram() {
        super(new ProgramAssemblyInfo("identity"));
    }

    public void useTexture(int texture2D, int type) {
        useTexture(0, texture2D, type);
    }
}
