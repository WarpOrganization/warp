package pl.warp.engine.graphics.rendering.scene.program;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.rendering.scene.tesselation.NoTessellationRenderingProgram;
import pl.warp.engine.graphics.rendering.scene.tesselation.SceneTessellationMode;
import pl.warp.engine.graphics.rendering.scene.tesselation.TessellationRenderingProgram;
import pl.warp.engine.graphics.tessellation.Tessellator;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
@Service
public class SceneRenderingProgramManager {

    private CameraHolder cameraHolder;

    private TessellationRenderingProgram bezierTessellationRenderingProgram;
    private TessellationRenderingProgram flatTessellationRenderingProgram;
    private NoTessellationRenderingProgram noTessellationRenderingProgram;

    public SceneRenderingProgramManager(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    public void init() {
        this.flatTessellationRenderingProgram = new TessellationRenderingProgram(Tessellator.FLAT_TESSELLATOR_LOCATION);
        this.bezierTessellationRenderingProgram = new TessellationRenderingProgram(Tessellator.BEZIER_TESSELLATOR_LOCATION);
        this.noTessellationRenderingProgram = new NoTessellationRenderingProgram();
    }

    public void update() {
        bezierTessellationRenderingProgram.use();
        bezierTessellationRenderingProgram.useCamera(cameraHolder.getCamera());
        flatTessellationRenderingProgram.use();
        flatTessellationRenderingProgram.useCamera(cameraHolder.getCamera());
        noTessellationRenderingProgram.use();
        noTessellationRenderingProgram.useCamera(cameraHolder.getCamera());
    }

    public void prepareProgram(
            Material material,
            MatrixStack matrixStack,
            SceneTessellationMode tesselationMode
    ) {
        switch (tesselationMode) {
            case NONE:
                noTessellationRenderingProgram.use();
                noTessellationRenderingProgram.useMaterial(material);
                noTessellationRenderingProgram.useMatrixStack(matrixStack);
                break;
            case FLAT:
                flatTessellationRenderingProgram.use();
                flatTessellationRenderingProgram.useMaterial(material);
                flatTessellationRenderingProgram.useMatrixStack(matrixStack);
                break;
            case BEZIER:
                bezierTessellationRenderingProgram.use();
                bezierTessellationRenderingProgram.useMaterial(material);
                bezierTessellationRenderingProgram.useMatrixStack(matrixStack);
                break;
        }
    }

    public void destroy() {
        bezierTessellationRenderingProgram.delete();
        flatTessellationRenderingProgram.delete();
        noTessellationRenderingProgram.delete();
    }
}
