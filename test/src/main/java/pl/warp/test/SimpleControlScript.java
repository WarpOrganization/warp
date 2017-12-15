package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.annotation.ContextService;
import pl.warp.engine.core.script.annotation.OwnerProperty;
import pl.warp.engine.input.Input;

import static java.awt.event.KeyEvent.*;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */
public class SimpleControlScript extends Script {


    private static final float CAMERA_SPEED = 0.025f;
    private static final float ROT_SPEED = 0.0001f;

    private float cameraSpeed = CAMERA_SPEED;

    public SimpleControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(TransformProperty.NAME)
    private TransformProperty transformProperty;

    @ContextService
    private Input input;


    private Vector3f movementVector = new Vector3f();

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        rotate(delta);
        move(delta);
    }

    private void move(int delta) {
        if (input.isKeyDown(VK_SHIFT))
            cameraSpeed = CAMERA_SPEED * 3;
        else cameraSpeed = CAMERA_SPEED;

        movementVector.zero();
        if (input.isKeyDown(VK_W)) {
            movementVector.add(0, 0, -1);
        }
        if (input.isKeyDown(VK_S))
            movementVector.add(0, 0, 1);
        if (input.isKeyDown(VK_A))
            movementVector.add(-1, 0, 0);
        if (input.isKeyDown(VK_D))
            movementVector.add(1, 0, 0);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(cameraSpeed * delta);
            transformProperty.move(movementVector);
        }
    }


    private void rotate(int delta) {
        Vector2f cursorPositionDelta = input.getCursorPositionDelta();
        transformProperty.rotateX(-cursorPositionDelta.y * ROT_SPEED * delta);
        transformProperty.rotateY(-cursorPositionDelta.x * ROT_SPEED * delta);
        if (input.isKeyDown(VK_Q))
            transformProperty.rotateZ(ROT_SPEED * delta);
        if (input.isKeyDown(VK_E))
            transformProperty.rotateZ(-ROT_SPEED * delta);
    }
}
