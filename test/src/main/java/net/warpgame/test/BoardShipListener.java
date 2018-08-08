package net.warpgame.test;

import net.warpgame.content.BoardShipEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.graphics.camera.Camera;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.QuaternionCamera;
import net.warpgame.engine.graphics.utility.projection.PerspectiveMatrix;
import net.warpgame.engine.graphics.window.Display;
import net.warpgame.engine.physics.simplified.SimplifiedPhysicsProperty;
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
        super(owner, Event.getTypeId(BoardShipEvent.class));
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
        Component shipComponent = componentRegistry.getComponent(event.getShipComponentId());
        shipComponent.addProperty(new SimplifiedPhysicsProperty(10f));
        shipComponent.addScript(MultiplayerControlScript.class);

        Component cameraPivot = new SceneComponent(shipComponent);
        TransformProperty pivotTransform = new TransformProperty().move(0, 2, 0);
        cameraPivot.addProperty(pivotTransform);
        cameraPivot.addScript(MultiplayerCameraControlScript.class);

        Component cameraComponent = new SceneComponent(cameraPivot, 1000000001);
        TransformProperty cameraTransform = new TransformProperty();
        cameraTransform.move(new Vector3f(20, 0, 0)).rotateY((float) (Math.PI / 2));
        cameraComponent.addProperty(cameraTransform);
        cameraComponent.addScript(CameraZoomControlScript.class);

        Camera camera = new QuaternionCamera(cameraComponent, projection);
        cameraHolder.setCamera(camera);
    }
}
