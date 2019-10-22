package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;
import net.warpgame.engine.graphics.memory.scene.ubo.VulkanTransform;
import net.warpgame.engine.graphics.utility.Destroyable;

import java.lang.ref.WeakReference;

/**
 * @author MarconZet
 * Created 22.10.2019
 */
public class VulkanRender implements Destroyable {
    private WeakReference<Component> origin;
    private VulkanTransform transform;
    private StaticMesh mesh;
    private Texture texture;


    public VulkanRender(Component component, VulkanTransform transform, StaticMesh mesh, Texture texture) {
        this.origin = new WeakReference<>(component);
        this.transform = transform;
        this.mesh = mesh;
        this.texture = texture;
    }

    @Override
    public void destroy() {

    }
}
