package pl.warp.game;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 00
 */
public class GoatControlScript extends Script<Component> {

    private static final float MOUSE_ROTATION_SPEED_FACTOR = 0.2f;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final Vector3f RIGHT_VECTOR = new Vector3f(1, 0, 0);
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);

    private float movementSpeed;
    private float rotationSpeed;
    private PhysicalBodyProperty bodyProperty;
    private GLFWInput input;
    private final float brakingForce;
    private final float arrowKeysRottationSpeed;

    private Vector3f forwardVector = new Vector3f();
    private Vector3f rightVector = new Vector3f();
    private Vector3f upVector = new Vector3f();

    public GoatControlScript(Component owner, GLFWInput input, float movementSpeed, float rotationSpeed, float brakingForce, float arrowKeysRottationSpeed) {
        super(owner);
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.input = input;
        this.brakingForce = brakingForce;
        this.arrowKeysRottationSpeed = arrowKeysRottationSpeed;
    }

    @Override
    public void onInit() {
        this.bodyProperty = getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
    }

    @Override
    public void onUpdate(int delta) {
        updateDirections();
        desiredTorque.set(0, 0, 0);
        move(delta);
        rotate(delta);
        moveAngular(delta);
    }

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getActualRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
        goatFullRotation.transform(rightVector.set(RIGHT_VECTOR)).negate();
        goatFullRotation.transform(upVector.set(UP_VECTOR)).negate();
    }

    private void move(int delta) {
        if (input.isKeyDown(GLFW.GLFW_KEY_W))
            move(forwardVector, movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_S))
            move(forwardVector, -movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_A))
            move(rightVector, movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_D))
            move(rightVector, -movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            brake(delta);
        if(input.isKeyDown(GLFW.GLFW_KEY_P))
            stop();
        if (input.isKeyDown(GLFW.GLFW_KEY_UP))
            addDesiredTorque(arrowKeysRottationSpeed, 0, 0);
        if (input.isKeyDown(GLFW.GLFW_KEY_DOWN))
            addDesiredTorque(-arrowKeysRottationSpeed, 0, 0);
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT))
            addDesiredTorque(0, arrowKeysRottationSpeed, 0);
        if (input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
            addDesiredTorque(0, -arrowKeysRottationSpeed, 0);
    }

    private Vector3f tmpForce = new Vector3f();
    private Vector3f desiredTorque = new Vector3f();

    private void move(Vector3f direction, float force) {
        bodyProperty.applyForce(tmpForce.set(direction).mul(force));
    }

    private Vector3f torqueChange = new Vector3f();

    private void moveAngular(float delta) {
        if (desiredTorque.equals(bodyProperty.getTorque())) return;
        torqueChange.set(bodyProperty.getTorque());
        torqueChange.sub(desiredTorque);
        torqueChange.negate();
        if (torqueChange.length() > rotationSpeed * delta / bodyProperty.getInteria()) {
            torqueChange.normalize();
            torqueChange.mul(rotationSpeed);
            torqueChange.mul(delta);
        } else {
            torqueChange.mul(bodyProperty.getInteria());
        }
        bodyProperty.addTorque(torqueChange);
    }

    private void addDesiredTorque(float x, float y, float z) {
        desiredTorque.add(x, y, z);
    }

    private void rotate(int delta) {
        Vector2f cursorPosDelta = input.getCursorPositionDelta();
        Vector2f rotation = new Vector2f();
        cursorPosDelta.mul(rotationSpeed * delta * MOUSE_ROTATION_SPEED_FACTOR, rotation);
        bodyProperty.addTorque(new Vector3f(-rotation.y, -rotation.x, 0));
    }

    private void stop() {
        bodyProperty.setVelocity(new Vector3f(0));
        bodyProperty.setTorque(new Vector3f(0));
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
