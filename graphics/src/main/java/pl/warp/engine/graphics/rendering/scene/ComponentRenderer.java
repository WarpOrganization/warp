package pl.warp.engine.graphics.rendering.scene;

import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    private CameraHolder cameraHolder;
    private SceneRenderingProgram sceneRenderingProgram;

    public ComponentRenderer(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    public void init() {
        this.sceneRenderingProgram = new SceneRenderingProgram();
    }

    public void initRendering() {
        sceneRenderingProgram.use();
        sceneRenderingProgram.useCamera(cameraHolder.getCamera());
    }

    private void applyTransformations(Component component) {
        TransformProperty property = component.getProperty(TransformProperty.NAME);
        applyTranslation(property);
        applyScale(property);
        applyRotation(property);
    }

    private void applyTranslation(TransformProperty translation) {
        matrixStack.translate(translation.getTranslation());
    }

    private void applyScale(TransformProperty scale) {
        matrixStack.scale(scale.getScale());
    }

    private void applyRotation(TransformProperty rotation) {
        matrixStack.rotate(rotation.getRotation());
    }

    public void renderComponent(Component component) {
        if(component.hasEnabledProperty(MeshProperty.NAME)){
            applyTransformations(component);
            sceneRenderingProgram.useMatrixStack(matrixStack);
            MeshProperty meshProperty = component.getProperty(MeshProperty.NAME);
            Mesh mesh = meshProperty.getMesh();
            mesh.render();
        }
    }

    public void enterChildren() {
        matrixStack.push();
    }

    public void leaveChildren() {
        matrixStack.pop();
    }

    public void destroy() {
        sceneRenderingProgram.delete();
    }

}
