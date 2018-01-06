package net.warpgame.servertest;

import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.common.transform.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.server.RemoteInput;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class MovementScript extends Script {

    @OwnerProperty(RemoteInputProperty.NAME)
    private RemoteInputProperty remoteInputProperty;

    private RemoteInput input;

    @OwnerProperty(TransformProperty.NAME)
    private TransformProperty transformProperty;

    private static final float ROT_SPEED = 0.001f;

    public MovementScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        input = remoteInputProperty.getRemoteInput();
    }

    @Override
    public void onUpdate(int delta) {
        move(delta);
        rotate(delta);
    }

    private Vector3f movementVector = new Vector3f();
    private Vector3f rotationVector = new Vector3f();

    private void move(int delta) {

        movementVector.zero();
        if (input.isForwardPressed()) {
            movementVector.add(-1, 0, 0);
        }
        if (input.isBackwardsPressed())
            movementVector.add(1, 0, 0);
        if (input.isLeftPressed())
            movementVector.add(0, 0, 1);
        if (input.isRightPressed())
            movementVector.add(0, 0, -1);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(0.025f * delta);
            transformProperty.move(movementVector);
        }
    }

    private void rotate(int delta) {
        if (input.isRotationUp())
            transformProperty.rotateZ(ROT_SPEED * delta);
        if (input.isRotationDown())
            transformProperty.rotateZ(-ROT_SPEED * delta);
        if (input.isRotationLeft())
            transformProperty.rotateY(ROT_SPEED * delta);
        if (input.isRotationRight())
            transformProperty.rotateY(-ROT_SPEED * delta);
    }
//    private void rotate(int delta) {
//        Vector2f cursorPositionDelta = input.getCursorPositionDelta();
//        transformProperty.rotateX(-cursorPositionDelta.y * ROT_SPEED * delta);
//        transformProperty.rotateY(-cursorPositionDelta.x * ROT_SPEED * delta);
//        if (input.isKeyDown(VK_Q))
//            transformProperty.rotateZ(ROT_SPEED * delta);
//        if (input.isKeyDown(VK_E))
//            transformProperty.rotateZ(-ROT_SPEED * delta);
//    }

}
