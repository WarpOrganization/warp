package net.warpgame.engine.graphics.rendering.ui.text.program;

import net.warpgame.engine.graphics.program.Program;
import net.warpgame.engine.graphics.program.ProgramAssemblyInfo;
import net.warpgame.engine.graphics.program.extendedglsl.ExtendedGLSLProgramCompiler;
import org.joml.Matrix4fc;

/**
 * @author MarconZet
 * Created 28.08.2018
 */
public class TextProgram extends Program {

    TextProgram(){
        super(new ProgramAssemblyInfo("text"), ExtendedGLSLProgramCompiler.DEFAULT_COMPILER);
    }

    public void useProjectionMatrix(Matrix4fc projectionMatrix) {

    }
}
