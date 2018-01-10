package net.warpgame.servertest;

import com.badlogic.gdx.physics.bullet.collision.Collision;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.common.transform.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.physics.FullPhysicsProperty;
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

    @OwnerProperty(FullPhysicsProperty.NAME)
    private FullPhysicsProperty physicsProperty;

    private static final float ROT_SPEED = 0.002f;
    private static final float MOV_SPEED = 0.04f;

    public MovementScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        input = remoteInputProperty.getRemoteInput();
        physicsProperty.getRigidBody().setActivationState(Collision.DISABLE_DEACTIVATION);
        physicsProperty.getRigidBody().activate();
    }

    @Override
    public void onUpdate(int delta) {
        move(delta);
        rotate(delta);
    }

    private Vector3f movementVector = new Vector3f();

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
            movementVector.mul(MOV_SPEED * delta);
            physicsProperty.applyCentralForce(movementVector);
        }
    }

    private Vector3f torqueVector = new Vector3f();

    private void rotate(int delta) {
        if (input.isRotationUp())
            torqueVector.add(0, 0, 1);
        if (input.isRotationDown())
            torqueVector.add(0, 0, -1);

        if (input.isRotationLeft())
            torqueVector.add(1, 0, 0);

        if (input.isRotationRight())
            torqueVector.add(-1, 0, 0);

        if (torqueVector.lengthSquared() >= 1) {
            torqueVector.mul(delta * ROT_SPEED);
            physicsProperty.applyTorque(torqueVector);
            torqueVector.set(0);
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
