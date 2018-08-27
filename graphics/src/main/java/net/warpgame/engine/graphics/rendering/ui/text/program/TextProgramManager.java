package net.warpgame.engine.graphics.rendering.ui.text.program;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import org.joml.Matrix4fc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MarconZet
 * Created 28.08.2018
 */

@Service
public class TextProgramManager {
    private static final Logger logger = LoggerFactory.getLogger(TextProgramManager.class);

    private TextProgram textProgram;
    private Matrix4fc projectionMatrix;

    public void init(){
        try {
            textProgram = new TextProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile image rendering program");
        }
    }

    public void update(){
        textProgram.use();
        textProgram.useProjectionMatrix(projectionMatrix);
    }

    public void prepareProgram(){
        textProgram.use();
    }

    public void destroy(){
        textProgram.delete();
    }

    public void setProjectionMatrix(Matrix4fc projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}
