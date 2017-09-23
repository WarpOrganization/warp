package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MeshProperty extends Property {

    public static final String MESH_PROPERTY_NAME = "mesh";

    private Mesh mesh;

    public MeshProperty(Mesh mesh) {
        super(MESH_PROPERTY_NAME);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
