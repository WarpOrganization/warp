package net.warpgame.engine.graphics.rendering.ui.program;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author MarconZet
 * Created 18.08.2018
 */
@Service
@Profile("graphics")
public class UiProgramManager {
    private static final Logger logger = LoggerFactory.getLogger(UiProgramManager.class);

    private UiProgram uiProgram;
    private Matrix4f projectionMatrix;

    public void init(){
        try {
            uiProgram = new UiProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile ui rendering program");
        }
    }

    public void update(){
        uiProgram.use();
        uiProgram.useProjectionMatrix(projectionMatrix);
    }

    public void prepareProgram(Matrix3x2f matrix, Texture2D texture){
        uiProgram.useTransformationMatrix(matrix);
        uiProgram.useTexture(texture);
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}
