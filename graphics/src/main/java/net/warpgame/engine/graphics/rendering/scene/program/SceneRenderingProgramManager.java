package net.warpgame.engine.graphics.rendering.scene.program;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.animation.AnimatedModelProperty;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.program.ShaderCompilationException;
import net.warpgame.engine.graphics.utility.MatrixStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
@Service
@Profile("graphics")
public class SceneRenderingProgramManager {

    private static final Logger logger = LoggerFactory.getLogger(SceneRenderingProgramManager.class);

    private CameraHolder cameraHolder;

    private SceneRenderingProgram sceneRenderingProgram;

    public SceneRenderingProgramManager(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    public void init() {
        try {
            this.sceneRenderingProgram = new SceneRenderingProgram();
        } catch (ShaderCompilationException e) {
            logger.error("Failed to compile scene rendering shaders");
        }
    }

    public void update() {
        sceneRenderingProgram.use();
        sceneRenderingProgram.useCamera(cameraHolder.getCameraProperty());
    }

    public void prepareProgram(
            Material material,
            MatrixStack matrixStack,
            AnimatedModelProperty animatedModelProperty
            ) {
        sceneRenderingProgram.use();
        sceneRenderingProgram.useMaterial(material);
        sceneRenderingProgram.useMatrixStack(matrixStack);
        sceneRenderingProgram.useAnimationData(animatedModelProperty);
    }

    public void destroy() {
        sceneRenderingProgram.delete();
    }
}
