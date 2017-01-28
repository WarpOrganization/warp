package pl.warp.ide.engine;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.BUTTON1;

/**
 * @author Jaca777
 *         Created 2017-01-26 at 12
 */
public class IDECameraControlScript extends GameScript<GameComponent> {

    private static final float CAMERA_SPEED = 0.025f;
    private static final float ROT_SPEED = 0.06f;

    private float cameraSpeed = CAMERA_SPEED;
    private Camera camera;

    public IDECameraControlScript(GameComponent owner) {
        super(owner);
        this.camera = owner.<CameraProperty>getProperty(CameraProperty.CAMERA_PROPERTY_NAME).getCamera();
    }

    @Override
    public void init() {

    }

    private Vector3f movementVector = new Vector3f();

    @Override
    public void update(int delta) {
        Input input = getContext().getInput();
        move(input, delta);
        rotate(input, delta);
    }

    private void move(Input input, int delta) {
        if(input.isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * 3;
        else cameraSpeed = CAMERA_SPEED;

        movementVector.zero();
        if (input.isKeyDown(VK_W))
            movementVector.add(0, 0, -1);
        if (input.isKeyDown(VK_S))
            movementVector.add(0, 0, 1);
        if (input.isKeyDown(VK_A))
            movementVector.add(-1, 0, 0);
        if (input.isKeyDown(VK_D))
            movementVector.add(1, 0, 0);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(cameraSpeed * delta);
            camera.move(movementVector);
        }
    }


    private void rotate(Input input, int delta) {
        if (input.isMouseButtonDown(BUTTON1)) {
            Vector2f cursorPositionDelta = input.getCursorPositionDelta();
            camera.rotateX(cursorPositionDelta.y * ROT_SPEED * delta);
            camera.rotateY(-cursorPositionDelta.x * ROT_SPEED * delta);
        }
        if(input.isKeyDown(VK_Q))
            camera.rotateZ(ROT_SPEED * delta * 0.01f);
        if(input.isKeyDown(VK_E))
            camera.rotateZ(-ROT_SPEED * delta * 0.01f);
    }
}
