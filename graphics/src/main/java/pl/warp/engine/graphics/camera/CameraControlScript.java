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

    private GLFWInput input;
    private float speed;
    private float movementSpeed;

    public CameraControlScript(Camera owner, GLFWInput input, float speed, float movementSpeed) {
        super(owner);
        this.input = input;
        this.speed = speed;
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
        cursorPosDelta.mul(speed * delta, rotation);
        camera.rotate(rotation.y, rotation.x, 0);
    }

    private void move(long delta) {
        Camera camera = getOwner();
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
            System.out.println("lul");
            Vector3f movement = new Vector3f();
            camera.getDirectionVector().mul(movementSpeed * delta, movement);
            camera.move(movement);
        }
    }

}
