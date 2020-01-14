package net.warpgame.engine.graphics.memory.scene.mesh;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.memory.scene.ubo.VulkanTransform;

/**
 * @author MarconZet
 * Created 11.05.2019
 */
public class MeshProperty extends Property {
    private StaticMesh mesh;
    private VulkanTransform vulkanTransform;

    public MeshProperty(StaticMesh mesh) {
        this.mesh = mesh;
        this.vulkanTransform = new VulkanTransform();
    }

    @Override
    public void init() {
        mesh.scheduleForLoad(this);
        vulkanTransform.scheduleForLoad(this);
    }

    public StaticMesh getMesh() {
        return mesh;
    }

    public VulkanTransform getVulkanTransform() {
        return vulkanTransform;
    }
}
