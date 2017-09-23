package pl.warp.engine.graphics.mesh;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MeshProperty extends Property {

    public static final String NAME = "mesh";

    private Mesh mesh;

    public MeshProperty(Mesh mesh) {
        super(NAME);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
