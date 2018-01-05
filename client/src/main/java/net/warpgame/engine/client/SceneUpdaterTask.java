package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

/**
 * @author Hubertus
 * Created 14.12.2017
 */
@Service
@RegisterTask(thread = "client")
public class SceneUpdaterTask extends EngineTask {

    private SerializedSceneHolder sceneHolder;
    private ComponentRegistry componentRegistry;

    public SceneUpdaterTask(SerializedSceneHolder sceneHolder, ComponentRegistry componentRegistry) {
        this.sceneHolder = sceneHolder;
        this.componentRegistry = componentRegistry;
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
            Component c = componentRegistry.getCompoenent(componentId);
            if (c != null) {
                translation.set(
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat());
                rotation.set(
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat(),
                        serializedScene.readFloat());

                TransformProperty transformProperty = c.getProperty(TransformProperty.NAME);
                transformProperty.setTranslation(translation);
                transformProperty.setRotation(rotation);
            } else System.out.println("Component with id " + componentId + " not present");
        }
    }

}
