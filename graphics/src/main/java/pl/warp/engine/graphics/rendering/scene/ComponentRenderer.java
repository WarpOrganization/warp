package pl.warp.engine.graphics.rendering.scene;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.GLErrors;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.rendering.scene.program.FlatTessellationRenderingProgram;
import pl.warp.engine.graphics.rendering.scene.program.NoTessellationRenderingProgram;
import pl.warp.engine.graphics.rendering.scene.program.TessellationRenderingProgram;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    private CameraHolder cameraHolder;

    //TODO move it away to scene rendering manager (or sth like this)
    private TessellationRenderingProgram tessellationRenderingProgram;
    private FlatTessellationRenderingProgram flatTessellationRenderingProgram;
    private NoTessellationRenderingProgram noTessellationRenderingProgram;

    public ComponentRenderer(CameraHolder cameraHolder) {
        this.cameraHolder = cameraHolder;
    }

    public void init() {
        this.tessellationRenderingProgram = new TessellationRenderingProgram();
        this.flatTessellationRenderingProgram = new FlatTessellationRenderingProgram();
        this.noTessellationRenderingProgram = new NoTessellationRenderingProgram();
    }

    public void initRendering() {
        tessellationRenderingProgram.use();
        tessellationRenderingProgram.useCamera(cameraHolder.getCamera());
        flatTessellationRenderingProgram.use();
        flatTessellationRenderingProgram.useCamera(cameraHolder.getCamera());
        noTessellationRenderingProgram.use();
        noTessellationRenderingProgram.useCamera(cameraHolder.getCamera());
        setupGL();
        GLErrors.checkOGLErrors();
    }

    private void setupGL() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER,0);
        GL11.glViewport(0,0, 1280, 920);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

    public void renderComponent(Component component) {
        if(component.hasEnabledProperty(MeshProperty.NAME)){
            applyTransformations(component);
            Material material = getMaterial(component);
            Mesh mesh = getMesh(component);
            drawMesh(material, mesh);
        }
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

    protected void drawMesh(Material material, Mesh mesh) {
        switch (material.getTesselationMode()) {
            case NONE:
                noTessellationRenderingProgram.use();
                noTessellationRenderingProgram.useMaterial(material);
                noTessellationRenderingProgram.useMatrixStack(matrixStack);
                mesh.draw();
                break;
            case FLAT:
                flatTessellationRenderingProgram.use();
                flatTessellationRenderingProgram.useMaterial(material);
                flatTessellationRenderingProgram.useMatrixStack(matrixStack);
                mesh.drawPatched();
                break;
            case FULL:
                tessellationRenderingProgram.use();
                tessellationRenderingProgram.useMaterial(material);
                tessellationRenderingProgram.useMatrixStack(matrixStack);
                mesh.drawPatched();
                break;
        }
    }

    protected Mesh getMesh(Component component) {
        MeshProperty meshProperty = component.getProperty(MeshProperty.NAME);
        return meshProperty.getMesh();
    }

    protected Material getMaterial(Component component) {
        MaterialProperty property = component.getProperty(MaterialProperty.NAME);
        return property.getMaterial();
    }

    public void enterChildren() {
        matrixStack.push();
    }

    public void leaveChildren() {
        matrixStack.pop();
    }

    public void destroy() {
        tessellationRenderingProgram.delete();
    }

}
