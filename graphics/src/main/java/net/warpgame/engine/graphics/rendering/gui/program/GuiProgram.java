package net.warpgame.engine.graphics.rendering.gui.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3f;

public class GuiProgram extends Program {
    private static final int TEX_LOCATION = 0;

    private int unifTransformationMatrix;

    public GuiProgram() {
        super(new ProgramAssemblyInfo("gui"), ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        setTextureLocation("tex", TEX_LOCATION);
        loadUniforms();
    }

    protected void loadUniforms(){
        unifTransformationMatrix = getUniformLocation("transformationMatrix");
    }

    public void useTexture(Texture2D texture) {
        useTexture(TEX_LOCATION, texture);
    }

    public void useMatrix(Matrix3f matrix){
        setUniformMatrix3(unifTransformationMatrix, matrix);
    }
}
