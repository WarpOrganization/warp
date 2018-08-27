package net.warpgame.engine.graphics.rendering.ui.image.program;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4fc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author MarconZet
 * Created 18.08.2018
 */
@Service
@Profile("graphics")
public class ImageProgramManager {
    private static final Logger logger = LoggerFactory.getLogger(ImageProgramManager.class);

    private ImageProgram imageProgram;
    private Matrix4fc projectionMatrix;

    public void init(){
        try {
            imageProgram = new ImageProgram();
        }catch(ShaderCompilationException e) {
            logger.error("Failed to compile image rendering program");
        }
    }

    public void update(){
        imageProgram.use();
        imageProgram.useProjectionMatrix(projectionMatrix);
    }

    public void prepareProgram(Matrix3x2fc matrix, Texture2D texture){
        imageProgram.use();
        imageProgram.useTransformationMatrix(matrix);
        imageProgram.useTexture(texture);
    }

    public void setProjectionMatrix(Matrix4fc projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void destroy(){
        imageProgram.delete();
    }
}
