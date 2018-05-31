package net.warpgame.engine.graphics.rendering.antialiasing.smaa.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.program.extendedglsl.loader.LocalProgramLoader;
import net.warpgame.engine.graphics.rendering.antialiasing.smaa.SMAAShadingSettings;
import net.warpgame.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 * Created 2018-01-13 at 22
 */
public class EdgePassProgram extends Program {

    private static final int ALBEDO_TEX_LOCATION = 0;

    public EdgePassProgram(SMAAShadingSettings smaaShadingSettings) {
        super(new ProgramAssemblyInfo("antialiasing/smaa/edge"),
                new ExtendedGLSLProgramCompiler(
                        smaaShadingSettings.getConstants(),
                        LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER
                )
        );
        setTextureLocation("albedoTex", ALBEDO_TEX_LOCATION);
    }

    public void useAlbedoTexture(Texture2D albedoTexture) {
        useTexture(ALBEDO_TEX_LOCATION, albedoTexture);
    }
}
