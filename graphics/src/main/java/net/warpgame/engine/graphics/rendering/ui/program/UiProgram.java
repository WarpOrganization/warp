package net.warpgame.engine.graphics.rendering.ui.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;

public class UiProgram extends Program {
    private static final int TEX_LOCATION = 0;

    private int unifTransformationMatrix;

    public UiProgram() {
        super(new ProgramAssemblyInfo("ui"), ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        setTextureLocation("tex", TEX_LOCATION);
        loadUniforms();
    }

    protected void loadUniforms(){
        unifTransformationMatrix = getUniformLocation("transformationMatrix");
    }

    public void useTexture(Texture2D texture) {
        useTexture(TEX_LOCATION, texture);
    }

    public void useMatrix(Matrix3x2f matrix){
        setUniformMatrix3x2(unifTransformationMatrix, matrix);
    }
}
