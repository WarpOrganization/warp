package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class RenderableMeshProperty extends Property<Component> {

    public static final String MESH_PROPERTY_NAME = "mesh";

    private Mesh mesh;

    public RenderableMeshProperty(Mesh mesh) {
        super(MESH_PROPERTY_NAME);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
