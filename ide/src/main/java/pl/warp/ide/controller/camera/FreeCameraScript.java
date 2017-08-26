package pl.warp.ide.controller.camera;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScriptWithInput;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.BUTTON1;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 12
 */
public class FreeCameraScript extends GameScriptWithInput<GameComponent> {

    private static final float CAMERA_SPEED = 0.025f;
    private static final float ROT_SPEED = 0.03f;

    private float cameraSpeed = CAMERA_SPEED;
    private Camera camera;

    public FreeCameraScript(GameComponent owner) {
        super(owner);
        this.camera = owner.<CameraProperty>getProperty(CameraProperty.CAMERA_PROPERTY_NAME).getCamera();
    }

    @Override
    public void init() {

    }

    private Vector3f movementVector = new Vector3f();

    @Override
    public void update(int delta) {
        move(delta);
        rotate(delta);
    }

    private void move(int delta) {
        if(getInputHandler().isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * 3;
        else cameraSpeed = CAMERA_SPEED;

        movementVector.zero();
        if (getInputHandler().isKeyDown(VK_W))
            movementVector.add(0, 0, -1);
        if (getInputHandler().isKeyDown(VK_S))
            movementVector.add(0, 0, 1);
        if (getInputHandler().isKeyDown(VK_A))
            movementVector.add(-1, 0, 0);
        if (getInputHandler().isKeyDown(VK_D))
            movementVector.add(1, 0, 0);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(cameraSpeed * delta);
            camera.move(movementVector);
        }
    }


    private void rotate(int delta) {
        if (getInputHandler().isMouseButtonDown(BUTTON1)) {
            Vector2f cursorPositionDelta = getInputHandler().getCursorPositionDelta();
            camera.rotateX(cursorPositionDelta.y * ROT_SPEED * delta);
            camera.rotateY(-cursorPositionDelta.x * ROT_SPEED * delta);
        }
        if(getInputHandler().isKeyDown(VK_Q))
            camera.rotateZ(ROT_SPEED * delta * 0.01f);
        if(getInputHandler().isKeyDown(VK_E))
            camera.rotateZ(-ROT_SPEED * delta * 0.01f);
    }
}
