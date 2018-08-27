package net.warpgame.engine.graphics.rendering.ui.image.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4fc;

public class ImageProgram extends Program {
    private static final int TEX_LOCATION = 0;

    private int unifTransformationMatrix;
    private int unifProjectionMatrix;


    public ImageProgram() {
        super(new ProgramAssemblyInfo("image"), ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
        setTextureLocation("tex", TEX_LOCATION);
        loadUniforms();
    }

    protected void loadUniforms(){
        unifTransformationMatrix = getUniformLocation("transformationMatrix");
        unifProjectionMatrix = getUniformLocation("projectionMatrix");
    }

    public void useTexture(Texture2D texture) {
        useTexture(TEX_LOCATION, texture);
    }

    public void useTransformationMatrix(Matrix3x2fc matrix){
        setUniformMatrix3x2(unifTransformationMatrix, matrix);
    }

    public void useProjectionMatrix(Matrix4fc matrix){
        setUniformMatrix4(unifProjectionMatrix, matrix);
    }
}
