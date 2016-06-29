package pl.warp.engine.graphics.property;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.mesh.Mesh;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MeshProperty extends Property<Component> {

    public static final String MESH_PROPERTY_NAME = "mesh";

    private Mesh mesh;

    public MeshProperty(Component owner, Mesh mesh) {
        super(owner, MESH_PROPERTY_NAME);
        this.mesh = mesh;
    }
}
