package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.annotation.OwnerProperty;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.input.Input;
import pl.warp.engine.physics.PhysicsProperty;

import java.awt.event.KeyEvent;

/**
 * @author Jaca777
 * Created 2016-07-08 at 00
 */
public class GoatControlScript extends Script {

    private static final float MOUSE_ROTATION_SPEED_FACTOR = 0.2f;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final Vector3f RIGHT_VECTOR = new Vector3f(1, 0, 0);
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);

    @OwnerProperty(name = PhysicsProperty.PHYSICS_PROPERTY_NAME)
    private PhysicsProperty bodyProperty;

    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    private GunProperty gunProperty;

    @OwnerProperty(name = GoatProperty.GOAT_PROPERTY_NAME)
    private GoatProperty goatProperty;


    private Vector3f forwardVector = new Vector3f();
    private Vector3f rightVector = new Vector3f();
    private Vector3f upVector = new Vector3f();

    public GoatControlScript(Component owner) {
        super(owner);
    }


    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        updateDirections();
        desiredTorque.set(0, 0, 0);
        move(delta);
        rotate(delta);
//        moveAngular(delta);
        useGun();
    }

    private Vector3f movement = new Vector3f();


    private void useGun() {
        Input input = ((GameContext) getContext()).getInput();
        getOwner().<GunProperty>getPropertyIfExists(GunProperty.GUN_PROPERTY_NAME).ifPresent(
                c -> {
                    if (input.isKeyDown(KeyEvent.VK_CONTROL)) c.setTriggered(true);
                    else c.setTriggered(false);
                });
    }

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
        goatFullRotation.transform(rightVector.set(RIGHT_VECTOR)).negate();
        goatFullRotation.transform(upVector.set(UP_VECTOR)).negate();
    }

    private void move(int delta) {
        Input input = ((GameContext) getContext()).getInput();
        if (input.isKeyDown(KeyEvent.VK_W))
            move(forwardVector, goatProperty.getMovementSpeed() * delta);
        if (input.isKeyDown(KeyEvent.VK_S))
            move(forwardVector, -goatProperty.getMovementSpeed() * delta);
        if (input.isKeyDown(KeyEvent.VK_A))
            move(rightVector, goatProperty.getMovementSpeed() * delta);
        if (input.isKeyDown(KeyEvent.VK_D))
            move(rightVector, -goatProperty.getMovementSpeed() * delta);
        if (input.isKeyDown(KeyEvent.VK_M))
            toggleAngularDamping();
        if (input.isKeyDown(KeyEvent.VK_N))
            toggleLinearDamping();
        if (input.isKeyDown(KeyEvent.VK_P))
            stopBody();
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
            gunProperty.setTriggered(true);
        else
            gunProperty.setTriggered(false);
//        if (input.isKeyDown(KeyEvent.VK_UP))
//            addDesiredTorque(goatProperty.getArrowKeysRotationSpeed(), 0, 0);
//        if (input.isKeyDown(KeyEvent.VK_DOWN))
//            addDesiredTorque(-goatProperty.getArrowKeysRotationSpeed(), 0, 0);
//        if (input.isKeyDown(KeyEvent.VK_LEFT))
//            addDesiredTorque(0, goatProperty.getArrowKeysRotationSpeed(), 0);
//        if (input.isKeyDown(KeyEvent.VK_RIGHT))
//            addDesiredTorque(0, -goatProperty.getArrowKeysRotationSpeed(), 0);
//        if (input.isKeyDown(KeyEvent.VK_Q))
//            addDesiredTorque(0, 0, goatProperty.getArrowKeysRotationSpeed());
//        if (input.isKeyDown(KeyEvent.VK_E))
//            addDesiredTorque(0, 0, -goatProperty.getArrowKeysRotationSpeed());
    }

    private Vector3f tmpForce = new Vector3f();
    private Vector3f desiredTorque = new Vector3f();
    private Vector3 tmp = new Vector3();

    private void move(Vector3f direction, float force) {
        tmpForce.set(direction).mul(force);
        bodyProperty.getRigidBody().applyCentralImpulse(tmp.set(tmpForce.x, tmpForce.y, tmpForce.z));
    }

    private boolean linearDamping = false;
    private boolean angularDamping = true;

    private void setDamping() {
        if (linearDamping)
            if (angularDamping)
                bodyProperty.getRigidBody().setDamping(goatProperty.getLinearDamping(), goatProperty.getAngularDamping());
            else
                bodyProperty.getRigidBody().setDamping(goatProperty.getLinearDamping(), 0);
        else if (angularDamping)
            bodyProperty.getRigidBody().setDamping(0, goatProperty.getAngularDamping());
        else
            bodyProperty.getRigidBody().setDamping(0, 0);
    }

    private long lastLinearDampingToggle = 0;

    private void toggleLinearDamping() {
        if (System.currentTimeMillis() - lastLinearDampingToggle > 1000) {
            linearDamping = !linearDamping;
            setDamping();
            System.out.println("linear damping " + linearDamping);
            lastLinearDampingToggle = System.currentTimeMillis();
        }
    }

    private long lastAngularDampingToggle = 0;

    private void toggleAngularDamping() {
        if (System.currentTimeMillis() - lastAngularDampingToggle > 1000) {
            angularDamping = !angularDamping;
            setDamping();
            System.out.println("angular damping " + angularDamping);
            lastAngularDampingToggle = System.currentTimeMillis();
        }
    }

    private Vector3f torqueChange = new Vector3f();

//    private void moveAngular(float delta) {
//        if (desiredTorque.equals(bodyProperty.getAngularVelocity())) return;
//        torqueChange.set(bodyProperty.getAngularVelocity());
//        torqueChange.sub(desiredTorque);
//        torqueChange.negate();
//        if (torqueChange.length() > goatProperty.getRotationSpeed() * delta / bodyProperty.getUniversalRotationInertia()) {
//            torqueChange.normalize();
//            torqueChange.mul(goatProperty.getRotationSpeed());
//            torqueChange.mul(delta);
//        } else {
//            torqueChange.mul(bodyProperty.getUniversalRotationInertia());
//        }
//        bodyProperty.addAngularVelocity(torqueChange.div(bodyProperty.getUniversalRotationInertia()));
//    }
//
//    private void addDesiredTorque(float x, float y, float z) {
//        desiredTorque.add(x, y, z);
//    }

    private void rotate(int delta) {
  /*      Vector2f cursorPosDelta = input.getCursorPositionDelta();
        Vector2f rotation = new Vector2f();
        cursorPosDelta.mul(goatProperty.getRotationSpeed() * delta * MOUSE_ROTATION_SPEED_FACTOR, rotation);
        bodyProperty.addAngularVelocity(new Vector3f(-rotation.y, -rotation.x, 0));
  */
    }

    private void stopBody() {
        bodyProperty.getRigidBody().setAngularVelocity(new Vector3(0, 0, 0));
        bodyProperty.getRigidBody().setLinearVelocity(new Vector3(0, 0, 0));
    }
}
