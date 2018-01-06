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
//        rotate(delta);
    }

    private Vector3f movementVector = new Vector3f();
    private Vector3f rotationVector = new Vector3f();

    private void move(int delta) {

        movementVector.zero();
        if (input.isForwardPressed()) {
            movementVector.add(0, 0, -1);
        }
        if (input.isBackwardsPressed())
            movementVector.add(0, 0, 1);
        if (input.isLeftPressed())
            movementVector.add(-1, 0, 0);
        if (input.isRightPressed())
            movementVector.add(1, 0, 0);
        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf());
        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(0.025f * delta);
            transformProperty.move(movementVector);
        }
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
