package pl.warp.test;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.script.GameScript;
import pl.warp.engine.game.script.OwnerProperty;

import java.awt.event.KeyEvent;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 00
 */
public class GoatControlScript extends GameScript<GameComponent> {

    private static final float MOUSE_ROTATION_SPEED_FACTOR = 0.2f;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final Vector3f RIGHT_VECTOR = new Vector3f(1, 0, 0);
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);

    private float movementSpeed;
    private float rotationSpeed;

    @OwnerProperty(name = PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME)
    private PhysicalBodyProperty bodyProperty;

    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    private GunProperty gunProperty;

    private final float brakingForce;
    private final float arrowKeysRottationSpeed;

    private Vector3f forwardVector = new Vector3f();
    private Vector3f rightVector = new Vector3f();
    private Vector3f upVector = new Vector3f();

    public GoatControlScript(GameComponent owner, float movementSpeed, float rotationSpeed, float brakingForce, float arrowKeysRotationSpeed) {
        super(owner);
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.brakingForce = brakingForce;
        this.arrowKeysRottationSpeed = arrowKeysRotationSpeed;
    }


    @Override
    protected void init() {

    }

    @Override
    public void update(int delta) {
        updateDirections();
        desiredTorque.set(0, 0, 0);
        move(delta);
        rotate(delta);
        moveAngular(delta);
        useGun();
    }

    private Vector3f movement = new Vector3f();


    private void useGun() {
        Input input = getContext().getInput();
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
        Input input = getContext().getInput();
        if (input.isKeyDown(KeyEvent.VK_W))
            move(forwardVector, movementSpeed * delta);
        if (input.isKeyDown(KeyEvent.VK_S))
            move(forwardVector, -movementSpeed * delta);
        if (input.isKeyDown(KeyEvent.VK_A))
            move(rightVector, movementSpeed * delta);
        if (input.isKeyDown(KeyEvent.VK_D))
            move(rightVector, -movementSpeed * delta);
        if (input.isKeyDown(KeyEvent.VK_SPACE))
            brake(delta);
        if (input.isKeyDown(KeyEvent.VK_P))
            stopBody();
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
            gunProperty.setTriggered(true);
        else
            gunProperty.setTriggered(false);
        if (input.isKeyDown(KeyEvent.VK_UP))
            addDesiredTorque(arrowKeysRottationSpeed, 0, 0);
        if (input.isKeyDown(KeyEvent.VK_DOWN))
            addDesiredTorque(-arrowKeysRottationSpeed, 0, 0);
        if (input.isKeyDown(KeyEvent.VK_LEFT))
            addDesiredTorque(0, arrowKeysRottationSpeed, 0);
        if (input.isKeyDown(KeyEvent.VK_RIGHT))
            addDesiredTorque(0, -arrowKeysRottationSpeed, 0);
        if (input.isKeyDown(KeyEvent.VK_Q))
            addDesiredTorque(0, 0, arrowKeysRottationSpeed);
        if (input.isKeyDown(KeyEvent.VK_E))
            addDesiredTorque(0, 0, -arrowKeysRottationSpeed);
    }

    private Vector3f tmpForce = new Vector3f();
    private Vector3f desiredTorque = new Vector3f();

    private void move(Vector3f direction, float force) {
        bodyProperty.applyForce(tmpForce.set(direction).mul(force));
    }

    private Vector3f torqueChange = new Vector3f();

    private void moveAngular(float delta) {
        if (desiredTorque.equals(bodyProperty.getAngularVelocity())) return;
        torqueChange.set(bodyProperty.getAngularVelocity());
        torqueChange.sub(desiredTorque);
        torqueChange.negate();
        if (torqueChange.length() > rotationSpeed * delta / bodyProperty.getUniversalRotationInertia()) {
            torqueChange.normalize();
            torqueChange.mul(rotationSpeed);
            torqueChange.mul(delta);
        } else {
            torqueChange.mul(bodyProperty.getUniversalRotationInertia());
        }
        bodyProperty.addAngularVelocity(torqueChange.div(bodyProperty.getUniversalRotationInertia()));
    }

    private void addDesiredTorque(float x, float y, float z) {
        desiredTorque.add(x, y, z);
    }

    private void rotate(int delta) {
  /*      Vector2f cursorPosDelta = input.getCursorPositionDelta();
        Vector2f rotation = new Vector2f();
        cursorPosDelta.mul(rotationSpeed * delta * MOUSE_ROTATION_SPEED_FACTOR, rotation);
        bodyProperty.addAngularVelocity(new Vector3f(-rotation.y, -rotation.x, 0));
  */
    }

    private void stopBody() {
        bodyProperty.setVelocity(new Vector3f(0));
        bodyProperty.setAngularVelocity(new Vector3f(0));
    }

    private Vector3f brakingVector = new Vector3f();

    private void brake(int delta) {
        brakingVector.set(bodyProperty.getVelocity());
        if (brakingVector.length() > brakingForce / bodyProperty.getMass()) {
            brakingVector.normalize();
            brakingVector.negate();
            brakingVector.mul(brakingForce);
        } else {
            brakingVector.negate();
            brakingVector.mul(bodyProperty.getMass());
        }
        brakingVector.mul(delta);
        bodyProperty.applyForce(brakingVector);
    }

}
