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
public class BlendPassProgram extends Program {

    private static final int EDGE_TEX_LOCATION = 0;
    private static final int AREA_TEX_LOCATION = 1;
    private static final int SEARCH_TEX_LOCATION = 2;

    public BlendPassProgram(SMAAShadingSettings smaaShadingSettings) {
        super(new ProgramAssemblyInfo("antialiasing/smaa/blend"),
                new ExtendedGLSLProgramCompiler(
                        smaaShadingSettings.getConstants(),
                        LocalProgramLoader.DEFAULT_LOCAL_PROGRAM_LOADER
                )
        );
        initialize();
    }

    private void initialize() {
        setTextureLocation("edgeTex", EDGE_TEX_LOCATION);
        setTextureLocation("areaTex", AREA_TEX_LOCATION);
        setTextureLocation("searchTex", SEARCH_TEX_LOCATION);
    }

    public void useTextures(Texture2D edgeTex, Texture2D areaTex, Texture2D searchTex) {
        useTexture(EDGE_TEX_LOCATION, edgeTex);
        useTexture(AREA_TEX_LOCATION, areaTex);
        useTexture(SEARCH_TEX_LOCATION, searchTex);
    }
}
