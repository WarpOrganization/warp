package net.warpgame.engine.graphics.tessellation.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class TessellationProgram extends Program {

    private static final int DISPLACEMENT_SAMPLER = 1;

    private int unifTessellationLevel;

    public TessellationProgram(String tessellator) {
        super(new ProgramAssemblyInfo()
                .setTesselator(tessellator)
                .setVertexShaderLocation("tessellator/vert"),
                ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        loadUniforms();
        loadSamplers();
    }
    protected void loadUniforms() {
        this.unifTessellationLevel = getUniformLocation("tessLevel");
    }

    protected void loadSamplers() {
        setTextureLocation("displacementMap", DISPLACEMENT_SAMPLER);
    }

    public void setTessellationLevel(float level) {
        setUniformf(unifTessellationLevel, level);
    }

}
