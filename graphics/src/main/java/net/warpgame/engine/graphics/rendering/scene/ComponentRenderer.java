package net.warpgame.engine.graphics.rendering.scene;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.IndexedMesh;
import net.warpgame.engine.graphics.rendering.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.rendering.scene.program.SceneRenderingProgramManager;
import net.warpgame.engine.graphics.rendering.scene.tesselation.SceneTessellationMode;
import net.warpgame.engine.graphics.rendering.scene.tesselation.TessellationModeProperty;
import net.warpgame.engine.graphics.utility.MatrixStack;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    private SceneRenderingProgramManager sceneRenderingProgramManager;
    private SceneTessellationMode defaultTessellationMode;


    public ComponentRenderer(Config config, SceneRenderingProgramManager sceneRenderingProgramManager) {
        this.defaultTessellationMode = config.getValue("graphics.rendering.defaultTessellation");
        this.sceneRenderingProgramManager = sceneRenderingProgramManager;
    }

    public void init() {
        this.sceneRenderingProgramManager.init();
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
    }


    public boolean renderComponentAndCheckIfDirty(Component component, boolean dirty) {
        if (component.hasEnabledProperty(MeshProperty.NAME)) {
            boolean componentDirty = applyTransformations(component, dirty);
            Material material = getMaterial(component);
            IndexedMesh mesh = getMesh(component);
            SceneTessellationMode tessellationMode = getTessellationMode(component);
            drawMesh(material, mesh, tessellationMode);
            return componentDirty;
        }
        return false;
    }

    private boolean applyTransformations(Component component, boolean dirty) {
        TransformProperty property = component.getProperty(TransformProperty.NAME);
        if(dirty || property.isDirty()) {
            applyTranslation(property);
            applyScale(property);
            applyRotation(property);
            property.updateCaches(matrixStack.topMatrix(), matrixStack.topRotationMatrix());
            return true;
        } else {
            matrixStack.setTop(property.getCachedNonrelativeTransform());
            matrixStack.setTopRotation(property.getCachedNonrelativeRotation());
            return false;
        }

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

    protected void drawMesh(Material material, IndexedMesh mesh, SceneTessellationMode runtimeTesselation) {
        sceneRenderingProgramManager.prepareProgram(material, matrixStack, runtimeTesselation);
        switch (runtimeTesselation) {
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
