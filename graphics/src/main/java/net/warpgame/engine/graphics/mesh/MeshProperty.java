package net.warpgame.engine.graphics.mesh;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-06-28 at 11
 */
public class MeshProperty extends Property {

    private StaticMesh mesh;

    public MeshProperty(StaticMesh mesh) {
        this.mesh = mesh;
    }

    public StaticMesh getMesh() {
        return mesh;
    }
}
