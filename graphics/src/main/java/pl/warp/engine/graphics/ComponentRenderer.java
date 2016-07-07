package pl.warp.engine.graphics;

import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.scene.Component;
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

    public ComponentRenderer(ComponentRendererProgram program) {
        this.program = program;
        setupRendering();
    }

    private void setupRendering() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public void render(Component component) {
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
