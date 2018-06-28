package net.warpgame.engine.graphics.rendering.scene;

import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.animation.AnimatedModelProperty;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.IndexedMesh;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.rendering.scene.program.SceneRenderingProgramManager;
import net.warpgame.engine.graphics.utility.MatrixStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
public class ComponentRenderer {

    private MatrixStack matrixStack = new MatrixStack();

    private SceneRenderingProgramManager sceneRenderingProgramManager;

    public ComponentRenderer(SceneRenderingProgramManager sceneRenderingProgramManager) {
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
        if (component.hasEnabledProperty(Property.getTypeId(MeshProperty.class)) ||
                component.hasEnabledProperty(Property.getTypeId(AnimatedModelProperty.class))) {
            boolean componentDirty = applyTransformations(component, dirty);
            Material material = getMaterial(component);
            IndexedMesh mesh = getMesh(component);
            drawMesh(material, mesh, component.getPropertyOrNull(Property.getTypeId(AnimatedModelProperty.class)));
            return componentDirty;
        }
        return false;
    }

    private boolean applyTransformations(Component component, boolean dirty) {
        TransformProperty property = component.getProperty(Property.getTypeId(TransformProperty.class));
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

    protected void drawMesh(Material material, IndexedMesh mesh, AnimatedModelProperty animatedModelProperty) {
        sceneRenderingProgramManager.prepareProgram(material, matrixStack, animatedModelProperty);
        mesh.draw();
    }

    protected IndexedMesh getMesh(Component component) {
        IndexedMesh mesh = null;
        if(component.hasEnabledProperty(Property.getTypeId(MeshProperty.class))) {
            MeshProperty meshProperty = component.getProperty(Property.getTypeId(MeshProperty.class));
            mesh = meshProperty.getMesh();
        } else if (component.hasEnabledProperty(Property.getTypeId(AnimatedModelProperty.class))) {
            AnimatedModelProperty meshProperty = component.getProperty(Property.getTypeId(AnimatedModelProperty.class));
            mesh = meshProperty.getAnimatedModel().getMesh();
        }
        return mesh;
    }

    protected Material getMaterial(Component component) {
        MaterialProperty property = component.getProperty(Property.getTypeId(MaterialProperty.class));
        return property.getMaterial();
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
