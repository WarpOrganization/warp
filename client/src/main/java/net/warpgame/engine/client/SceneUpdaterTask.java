package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 14.12.2017
 */
@Service
@RegisterTask(thread = "client")
public class SceneUpdaterTask extends EngineTask {

    private SerializedSceneHolder sceneHolder;
    private ComponentRegistry componentRegistry;
    private UpdateBlockerService blockerService;

    public SceneUpdaterTask(SerializedSceneHolder sceneHolder,
                            ComponentRegistry componentRegistry,
                            UpdateBlockerService blockerService) {
        this.sceneHolder = sceneHolder;
        this.componentRegistry = componentRegistry;
        this.blockerService = blockerService;
    }


    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        if (sceneHolder.isSceneAvailable())
            updateScene(sceneHolder.getScene());
    }

    private Vector3f translation = new Vector3f();
    private Quaternionf rotation = new Quaternionf();

    private void updateScene(ByteBuf serializedScene) {
        while (serializedScene.isReadable()) {
            int componentId = serializedScene.readInt();
            Component c = componentRegistry.getComponent(componentId);
            if (c != null && !blockerService.isBlocked(c.getId())) {
                translation.set(
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat());
                rotation.set(
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat());
                try {
                    TransformProperty transformProperty = c.getProperty(Property.getTypeId(TransformProperty.class));
                    transformProperty.setTranslation(translation);
                    transformProperty.setRotation(rotation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Component with id " + componentId + " not present");
                serializedScene.readerIndex(serializedScene.readerIndex() + 7 * 4);
            }
        }
    }

}
