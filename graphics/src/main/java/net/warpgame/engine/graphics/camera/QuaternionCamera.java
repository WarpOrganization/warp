package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.common.transform.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.graphics.utility.projection.ProjectionMatrix;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2016-07-01 at 20
 */
public class QuaternionCamera implements Camera {

    private ProjectionMatrix projection;
    private Component cameraComponent;

    public QuaternionCamera(Component cameraComponent, ProjectionMatrix projection) {
        this.projection = projection;
        this.cameraComponent = cameraComponent;
    }

    private Matrix4f cameraMatrix = new Matrix4f();
    private Quaternionf rotation = new Quaternionf();
    private Matrix4f rotationMatrix = new Matrix4f();
    private Vector3f cameraPos = new Vector3f();

    @Override
    public synchronized void update(int d) {
        Transforms.getImmediateTransform(cameraComponent, cameraMatrix).invert();
        Transforms.getAbsoluteRotation(cameraComponent, rotation).get(rotationMatrix).invert();
        Transforms.getAbsolutePosition(cameraComponent, cameraPos);
    }

    @Override
    public synchronized Vector3f getPosition(Vector3f dest) {
        return dest.set(cameraPos);
    }

    @Override
    public synchronized Matrix4f getCameraMatrix() {
        return cameraMatrix;
    }

    @Override
    public synchronized ProjectionMatrix getProjectionMatrix() {
        return projection;
    }

    @Override
    public synchronized Matrix4f getRotationMatrix() {
        return rotationMatrix;
    }

    private Vector3f forwardVector = new Vector3f();

    @Override
    public Vector3f getForwardVector() {
        return rotation.positiveZ(forwardVector).negate();
    }

    private Vector3f rightVector = new Vector3f();

    @Override
    public Vector3f getRightVector() {
        return rotation.positiveX(rightVector).negate();
    }

    private Vector3f upVector = new Vector3f();

    @Override
    public Vector3f getUpVector() {
        return rotation.positiveY(upVector).negate();
    }

    @Override
    public Component getCameraComponent() {
        return cameraComponent;
    }
}
