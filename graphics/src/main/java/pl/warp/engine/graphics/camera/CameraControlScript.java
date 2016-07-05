package pl.warp.engine.graphics.camera;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.graphics.input.GLFWInput;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public class CameraControlScript extends Script<Camera> {

    private static final float KEY_ROTATION_SPEED_FACTOR = 5f;

    private GLFWInput input;
    private float rotationSpeed;
    private float movementSpeed;

    public CameraControlScript(Camera owner, GLFWInput input, float rotationSpeed, float movementSpeed) {
        super(owner);
        this.input = input;
        this.rotationSpeed = rotationSpeed;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate(long delta) {
        rotate(delta);
        move(delta);
    }

    @SuppressWarnings("SuspiciousNameCombination") //it's ok
    private void rotate(long delta) {
        Camera camera = getOwner();
        Vector2f cursorPosDelta = input.getCursorPositionDelta();
        Vector2f rotation = new Vector2f();
        cursorPosDelta.mul(rotationSpeed * delta, rotation);
        camera.rotate(rotation.y, rotation.x, 0);
        if (input.isKeyDown(GLFW.GLFW_KEY_E))
            camera.rotateZ(delta * rotationSpeed * KEY_ROTATION_SPEED_FACTOR);
        if (input.isKeyDown(GLFW.GLFW_KEY_Q))
            camera.rotateZ(-delta * rotationSpeed * KEY_ROTATION_SPEED_FACTOR);
    }

    private void move(long delta) {
        Camera camera = getOwner();
        if (input.isKeyDown(GLFW.GLFW_KEY_W)    ) {
            move(camera.getForwardVector(), movementSpeed * delta);
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_S)) {
            move(camera.getForwardVector(), -movementSpeed * delta);
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) {
            move(camera.getRightVector(), movementSpeed * delta);
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_D)) {
            move(camera.getRightVector(), -movementSpeed * delta);
        }
    }

    private void move(Vector3f direction, float posDelta) {
        Camera camera = getOwner();
        Vector3f movement = new Vector3f();
        direction.mul(posDelta, movement);
        camera.move(movement);
    }

}
