package net.warpgame.engine.graphics.mesh;

import net.warpgame.engine.core.property.Property;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class MeshProperty extends Property {
    private StaticMesh mesh;

    public MeshProperty(StaticMesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void init() {
        mesh.schedule(this);
    }
}
