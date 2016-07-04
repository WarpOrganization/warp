package pl.warp.engine.graphics.camera;

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
        if(input.isKeyDown(GLFW.GLFW_KEY_UP))
            camera.rotateX(-delta * speed);
        if(input.isKeyDown(GLFW.GLFW_KEY_DOWN))
            camera.rotateX(delta * speed);
        if(input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
            camera.rotateY(delta * speed);
        if(input.isKeyDown(GLFW.GLFW_KEY_LEFT))
            camera.rotateY(-delta * speed);
    }

    private void move(long delta) {
        Camera camera = getOwner();
        Vector3f movement = new Vector3f();
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
            camera.getForwardVector().mul(movementSpeed * delta, movement);
            camera.move(movement);
        }
    }

}
