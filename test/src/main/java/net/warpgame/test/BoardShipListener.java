package net.warpgame.test;

import net.warpgame.content.BoardShipEvent;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.graphics.camera.Camera;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.QuaternionCamera;
import net.warpgame.engine.graphics.utility.projection.PerspectiveMatrix;
import net.warpgame.engine.graphics.window.Display;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class BoardShipListener extends Listener<BoardShipEvent> {
    private final CameraHolder cameraHolder;
    private PerspectiveMatrix projection;
    private final ComponentRegistry componentRegistry;

    protected BoardShipListener(Component owner,
                                CameraHolder cameraHolder,
                                Display display,
                                ComponentRegistry componentRegistry) {
        super(owner, "boardShipEvent");
        this.cameraHolder = cameraHolder;
        projection = new PerspectiveMatrix(
                55f,
                0.1f,
                10000f,
                display.getWidth(),
                display.getHeight()
        );
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void handle(BoardShipEvent event) {
        Component cameraOwner = componentRegistry.getComponent(event.getShipComponentId());
        Component cameraComponent = new SceneComponent(cameraOwner, 1000000001);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(20, 5, 0));
        transformProperty.rotateY((float) (Math.PI/2));
        cameraComponent.addProperty(transformProperty);
        Camera camera = new QuaternionCamera(cameraComponent, new TransformProperty(), projection);
        cameraHolder.setCamera(camera);
        cameraOwner.addScript(MultiplayerControlScript.class);
    }
}
