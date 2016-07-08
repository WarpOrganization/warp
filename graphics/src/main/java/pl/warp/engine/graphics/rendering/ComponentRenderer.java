package pl.warp.engine.graphics.rendering;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.math.MatrixStack;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.particles.ParticlesProperty;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class ComponentRenderer {

    private ComponentRendererProgram program;
    private MatrixStack matrixStack = new MatrixStack();

    public ComponentRenderer(ComponentRendererProgram program) {
        this.program = program;
        setupRendering();
    }

    private void setupRendering() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void render(Component component) {
        matrixStack.push();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransformations(component);
        program.useMatrixStack(matrixStack);
        renderComponent(component);
        component.forEachChildren(this::render);
        matrixStack.pop();
    }

    private void applyTransformations(Component component) { //translate, then rotate, then scale
        TransformProperty property = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        applyTranslation(property);
        applyScale(property);
        applyRotation(property);
    }

    private void applyScale(TransformProperty scale) {
        matrixStack.scale(scale.getScale());
    }

    private void applyRotation(TransformProperty rotation) {
        matrixStack.rotate(rotation.getRotation());
    }

    private void applyTranslation(TransformProperty translation) {
        matrixStack.translate(translation.getTranslation());
    }

    private void renderComponent(Component component) {
        if (component.hasEnabledProperty(MeshProperty.MESH_PROPERTY_NAME))
            renderMesh(component);
        if (component.hasEnabledProperty(ParticlesProperty.PARTICLES_PROPERTY_NAME))
            renderParticles(component);
    }


    private void renderMesh(Component component) {
        if (component.hasEnabledProperty(MaterialProperty.MATERIAL_PROPERTY_NAME))
            useMaterial(component.getProperty(MaterialProperty.MATERIAL_PROPERTY_NAME));
        Mesh mesh = component.<MeshProperty>getProperty(MeshProperty.MESH_PROPERTY_NAME).getMesh();
        mesh.bind();
        mesh.render();
        mesh.finalizeRendering();
    }

    private void useMaterial(MaterialProperty property) {
        program.useMaterial(property.getMaterial());
    }

    private void renderParticles(Component component) {
        //TODO
    }
}
