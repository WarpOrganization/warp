package pl.warp.engine.graphics.rendering.scene;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.config.Config;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.GLErrors;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.IndexedMesh;
import pl.warp.engine.graphics.rendering.scene.mesh.MeshProperty;
import pl.warp.engine.graphics.rendering.scene.program.SceneRenderingProgramManager;
import pl.warp.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    private SceneRenderingProgramManager sceneRenderingProgramManager;
    private SceneTessellationMode defaultTessellationMode;
    private int query;


    public ComponentRenderer(Config config, SceneRenderingProgramManager sceneRenderingProgramManager) {
        this.defaultTessellationMode = config.getValue("graphics.rendering.defaultTessellation");
        this.sceneRenderingProgramManager = sceneRenderingProgramManager;
    }

    public void init() {
        this.sceneRenderingProgramManager.init();
        this.query = GL15.glGenQueries();
        setupGL();
    }

    private void setupGL() {
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, 1280, 920);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
    }

    public void initRendering() {
        this.sceneRenderingProgramManager.update();
        GLErrors.checkOGLErrors();
    }


    public void renderComponent(Component component) {
        if (component.hasEnabledProperty(MeshProperty.NAME)) {
            applyTransformations(component);
            Material material = getMaterial(component);
            IndexedMesh mesh = getMesh(component);
            SceneTessellationMode tessellationMode = getTessellationMode(component);
            drawMesh(material, mesh, tessellationMode);
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

    protected void drawMesh(Material material, IndexedMesh mesh, SceneTessellationMode tessellationMode) {
        sceneRenderingProgramManager.prepareProgram(material, matrixStack, tessellationMode);
        switch (tessellationMode) {
            case NONE:
                mesh.draw();
                break;
            case FLAT:
            case BEZIER:

                mesh.drawPatched();
                break;
        }
    }

    protected IndexedMesh getMesh(Component component) {
        MeshProperty meshProperty = component.getProperty(MeshProperty.NAME);
        return meshProperty.getMesh();
    }

    protected Material getMaterial(Component component) {
        MaterialProperty property = component.getProperty(MaterialProperty.NAME);
        return property.getMaterial();
    }

    protected SceneTessellationMode getTessellationMode(Component component) {
        TessellationModeProperty property = component.getPropertyOrNull(TessellationModeProperty.NAME);
        return (property == null) ? defaultTessellationMode : property.getTessellationMode();
    }

    public void enterChildren() {
        matrixStack.push();
    }

    public void leaveChildren() {
        matrixStack.pop();
    }

    public void destroy() {
        sceneRenderingProgramManager.destroy();
    }

}
