package pl.warp.game;

import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.graphics.math.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 00
 */
public class GoatControlScript extends Script<Component> {

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0,0,-1);

    private float movementSpeed;
    private float rotationSpeed;
    private PhysicalBodyProperty bodyProperty;
    private TransformProperty transformProperty;
    private GLFWInput input;

    private Vector3f forwardVector = new Vector3f();
    private Vector3f rightVector = new Vector3f();
    private Vector3f upVector = new Vector3f();

    public GoatControlScript(Component owner, GLFWInput input, float movementSpeed, float rotationSpeed) {
        super(owner);
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.input = input;
    }

    @Override
    public void onInit() {
        this.bodyProperty = getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        this.transformProperty = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    @Override
    public void onUpdate(long delta) {
        updateDirections();
        move(delta);
        rotate(delta);
    }

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getFullRotation(getOwner());
        goatFullRotation.transform(forwardVector.set(FORWARD_VECTOR));
        goatFullRotation.positiveX(rightVector).negate();
        goatFullRotation.positiveY(upVector).negate();
    }

    private void move(long delta) {
        if (input.isKeyDown(GLFW.GLFW_KEY_W))
            move(forwardVector, movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_S))
            move(forwardVector, -movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_A))
            move(rightVector, movementSpeed * delta);
        if (input.isKeyDown(GLFW.GLFW_KEY_D))
            move(rightVector, -movementSpeed * delta);
    }

    private Vector3f tmpForce = new Vector3f();
    private void move(Vector3f direction, float force) {
        bodyProperty.applyForce(tmpForce.set(direction).mul(force));
    }

    private void rotate(long delta) {
        Vector2f cursorPosDelta = input.getCursorPositionDelta();
        Vector2f rotation = new Vector2f();
        cursorPosDelta.mul(rotationSpeed * delta, rotation);
        transformProperty.rotate(-rotation.y, -rotation.x, 0);
    }

}
