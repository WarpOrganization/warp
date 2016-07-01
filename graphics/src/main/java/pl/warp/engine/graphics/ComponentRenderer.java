package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.property.MaterialProperty;
import pl.warp.engine.graphics.property.MeshProperty;
import pl.warp.engine.graphics.property.ParticlesProperty;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class ComponentRenderer {

    private ComponentRendererProgram program;

    public ComponentRenderer(ComponentRendererProgram program) {
        this.program = program;
    }

    public void render(Component component) {
        if (component.hasProperty(MeshProperty.MESH_PROPERTY_NAME))
            renderMesh(component);
        if (component.hasProperty(ParticlesProperty.PARTICLES_PROPERTY_NAME))
            renderParticles(component);
    }


    private void renderMesh(Component component) {
        if (component.hasProperty(MaterialProperty.MATERIAL_PROPERTY_NAME))
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
