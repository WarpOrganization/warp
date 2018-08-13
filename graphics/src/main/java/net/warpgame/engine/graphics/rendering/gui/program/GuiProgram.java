package net.warpgame.engine.graphics.rendering.gui.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.texture.Texture2D;

public class GuiProgram extends Program {
    private static final int TEX_LOCATION = 0;

    public GuiProgram() {
        super(new ProgramAssemblyInfo("gui"), ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        setTextureLocation("tex", TEX_LOCATION);
    }

    public void useTexture(Texture2D texture) {
        useTexture(TEX_LOCATION, texture);
    }
}
