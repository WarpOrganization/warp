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
public class NeighbourhoodPassProgram extends Program {

    private static int ALBEDO_TEX_LOCATION = 0;
    private static int BLEND_TEX_LOCATION = 1;

    public NeighbourhoodPassProgram(SMAAShadingSettings smaaShadingSettings) {
        super(new ProgramAssemblyInfo("antialiasing/smaa/neighbourhood"),
                new ExtendedGLSLProgramCompiler(
                        smaaShadingSettings.getConstants(),
                        LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER
                )
        );
        setTextureLocations();
    }

    private void setTextureLocations() {
        setTextureLocation("albedoTex", ALBEDO_TEX_LOCATION);
        setTextureLocation("blendTex", BLEND_TEX_LOCATION);
    }

    public void useTextures(Texture2D albedoTexture, Texture2D blendTexture) {
        useTexture(ALBEDO_TEX_LOCATION, albedoTexture);
        useTexture(BLEND_TEX_LOCATION, blendTexture);
    }
}
