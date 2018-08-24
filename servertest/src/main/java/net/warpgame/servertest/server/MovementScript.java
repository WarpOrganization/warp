package net.warpgame.servertest.server;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.property.Transforms;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.physics.Collision;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.server.RemoteInput;
import net.warpgame.servertest.RemoteInputProperty;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class MovementScript extends Script {

    @OwnerProperty(@IdOf(RemoteInputProperty.class))
    private RemoteInputProperty remoteInputProperty;

    private RemoteInput input;

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @OwnerProperty(@IdOf(FullPhysicsProperty.class))
    private FullPhysicsProperty physicsProperty;


    private static final float ROT_SPEED = 0.016f;
    private static final float MOV_SPEED = 0.04f;
    private float currentCruiseSpeedSquared = 0;
    private boolean automaticVelocityRestorationSystem = true; //AVR
    private boolean controlAugmentationSystem = true; //CAS
    private int systemsSwitchCounter = 0;

    public MovementScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        input = remoteInputProperty.getRemoteInput();
        physicsProperty.setActivationState(Collision.ActivationState.DISABLE_DEACTIVATION);
        physicsProperty.activate();
    }

    @Override
    public void onUpdate(int delta) {
        move(delta);
        rotate(delta);
        control(delta);
    }

    private void control(int delta) {
        systemsSwitchCounter += delta;
        if (systemsSwitchCounter > 200) {
            /*if(input.isCAS()) {
                controlAugmentationSystem = !controlAugmentationSystem;
                systemsSwitchCounter = 0;
            }
            if(input.isAVR()) {
                automaticVelocityRestorationSystem = !automaticVelocityRestorationSystem;
                systemsSwitchCounter = 0;
            }*/
        }
    }

    private Vector3f movementVector = new Vector3f();

    private void move(int delta) {
        movementVector.zero();

        Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf());
        Quaternionf rotationInverted = Transforms.getAbsoluteRotation(getOwner(), new Quaternionf()).invert();
        boolean manualMainEngineControl = input.isForwardPressed() || input.isBackwardsPressed()
                || input.isLeftPressed() || input.isRightPressed();

        if (input.isForwardPressed())
            movementVector.add(-1, 0, 0);
        if (input.isBackwardsPressed())
            movementVector.add(1, 0, 0);
        if (input.isLeftPressed())
            movementVector.add(0, 0, 1);
        if (input.isRightPressed())
            movementVector.add(0, 0, -1);

        if (automaticVelocityRestorationSystem && !manualMainEngineControl) {
            if (physicsProperty.getVelocity().rotate(rotationInverted).x > currentCruiseSpeedSquared)
                movementVector.add(-1, 0, 0);
            else
                movementVector.add(1, 0, 0);
        }

        if (movementVector.lengthSquared() >= 1.0f) {
            movementVector.normalize();
            movementVector.rotate(rotation);
            movementVector.mul(MOV_SPEED * delta);
            physicsProperty.applyCentralForce(movementVector);

            if (manualMainEngineControl)
                currentCruiseSpeedSquared = physicsProperty.getVelocity().rotate(rotationInverted).x;
        }
        movementVector.zero();

        if (automaticVelocityRestorationSystem)
        {
            //Vector pointing ship front
            Vector3f  frontVector =  (new Vector3f(-1, 0, 0)).rotate(rotation);
            //Vector pointing ship velocity direction
            Vector3f  velocityVector =  physicsProperty.getVelocity();

            //movementVector = new Vector3f(frontVector);
            //the parameter in next line is component of velocityVector parallel to frontVector
            velocityVector.sub(frontVector.mul(frontVector.dot(velocityVector)/frontVector.lengthSquared()));
            //velocityVector is now storing component of velocityVector perpendicular to frontVector
            if (velocityVector.lengthSquared() >= 0.00001f) {
                velocityVector.mul(-1);
                velocityVector.normalize();
                velocityVector.mul(MOV_SPEED * delta);
                physicsProperty.applyCentralForce(velocityVector);
            }
        }
    }

    private Vector3f torqueVector = new Vector3f();

    private void rotate(int delta) {
        boolean manualManeuverEngineControl = input.isRotationUp() || input.isRotationDown() || input.isRotationLeft()
                || input.isRotationRight() || input.isRotationLeftX() || input.isRotationRightX();

        if (input.isRotationUp())
            torqueVector.add(0, 0, -1);
        if (input.isRotationDown())
            torqueVector.add(0, 0, 1);

        if (input.isRotationLeft())
            torqueVector.add(0, 1, 0);

        if (input.isRotationRight())
            torqueVector.add(0, -1, 0);

        if (input.isRotationLeftX())
            torqueVector.add(1, 0, 0);

        if (input.isRotationRightX())
            torqueVector.add(-1, 0, 0);

        if(controlAugmentationSystem && !manualManeuverEngineControl) {
            torqueVector = physicsProperty.getAngularVelocity();
            torqueVector.mul(-1);
            if (torqueVector.lengthSquared() >= 0.00001f) {
                torqueVector.normalize();
                torqueVector.mul(delta * ROT_SPEED);
                physicsProperty.applyTorque(torqueVector);
            }
        } else if (torqueVector.lengthSquared() >= 1) {
            torqueVector.normalize();
            torqueVector.mul(delta * ROT_SPEED);
            torqueVector.rotate(transformProperty.getRotation());
            physicsProperty.applyTorque(torqueVector);
        }
        torqueVector.set(0);


        /*Vector3f x = physicsProperty.getAngularVelocity();
        x.mul(-0.1f*delta);
        physicsProperty.applyTorque(x);*/
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
