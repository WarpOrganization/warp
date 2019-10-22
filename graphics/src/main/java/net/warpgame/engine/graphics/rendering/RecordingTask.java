package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.command.CommandPool;
import net.warpgame.engine.graphics.command.GraphicsQueue;
import net.warpgame.engine.graphics.command.StandardCommandPool;
import net.warpgame.engine.graphics.core.Device;
import net.warpgame.engine.graphics.memory.VulkanLoadTask;
import net.warpgame.engine.graphics.memory.scene.DescriptorPool;
import net.warpgame.engine.graphics.memory.scene.Loadable;
import net.warpgame.engine.graphics.memory.scene.material.MaterialProperty;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;
import net.warpgame.engine.graphics.memory.scene.ubo.VulkanTransform;
import net.warpgame.engine.graphics.rendering.ui.CanvasProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * @author MarconZet
 * Created 22.10.2019
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class RecordingTask extends EngineTask {
    private static final Logger logger = LoggerFactory.getLogger(RecordingTask.class);

    private boolean recreate;
    private CommandPool commandPool;
    private Set<Component> registeredComponents = Collections.newSetFromMap(new WeakHashMap<>());
    private Set<VulkanRender> vulkanRenders = new HashSet<>();

    private SceneHolder sceneHolder;

    private DescriptorPool descriptorPool;
    private GraphicsQueue graphicsQueue;
    private Device device;

    public RecordingTask(DescriptorPool descriptorPool, GraphicsQueue graphicsQueue, Device device, SceneHolder sceneHolder, VulkanLoadTask loadTask) {
        this.descriptorPool = descriptorPool;
        this.graphicsQueue = graphicsQueue;
        this.device = device;
        this.recreate = true;
        this.sceneHolder = sceneHolder;
    }

    @Override
    protected void onInit() {
        descriptorPool.create();
        commandPool = new StandardCommandPool(device, graphicsQueue);
    }

    @Override
    public void update(int delta) {
        if (recreate) {
            recreate = false;
            collectComponents(sceneHolder.getScene());
        }
    }

    @Override
    protected void onClose() {
        vulkanRenders.forEach(VulkanRender::destroy);
        commandPool.destroy();
        descriptorPool.destroy();
    }

    private void collectComponents(Component component) {
        if (component.hasProperty(Property.getTypeId(CanvasProperty.class))) return;
        component.forEachChildren(this::collectComponents);
        if(registeredComponents.contains(component)) return;
        MaterialProperty materialProperty = component.getPropertyOrNull(Property.getTypeId(MaterialProperty.class));
        MeshProperty meshProperty = component.getPropertyOrNull(Property.getTypeId(MeshProperty.class));
        if(materialProperty != null && meshProperty != null){
            Texture texture = materialProperty.getTexture();
            StaticMesh mesh = meshProperty.getMesh();
            VulkanTransform transform = meshProperty.getVulkanTransform();
            if(Stream.of(texture, mesh, transform).allMatch(x -> x.getLoadStatus() == Loadable.LOADED)){
                registeredComponents.add(component);
                VulkanRender render = new VulkanRender(component, transform, mesh, texture, descriptorPool, device);
                vulkanRenders.add(render);
            }
        }
    }
}
