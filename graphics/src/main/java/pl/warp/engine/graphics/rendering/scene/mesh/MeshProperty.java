package pl.warp.engine.graphics.rendering.scene.mesh;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MeshProperty extends Property {

    public static final String NAME = "mesh";

    private SceneMesh mesh;

    public MeshProperty(SceneMesh mesh) {
        super(NAME);
        this.mesh = mesh;
    }

    public SceneMesh getMesh() {
        return mesh;
    }
}
