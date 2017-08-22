package pl.warp.engine.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 20
 */
public class QuaternionCamera implements Camera {

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, 1);

    private ProjectionMatrix projection;
    private TransformProperty transform;
    private Component cameraComponent;

    public QuaternionCamera(Component cameraComponent, Vector3f position, TransformProperty transform, ProjectionMatrix projection) {
        this.projection = projection;
        this.transform = transform;
        this.cameraComponent = cameraComponent;
        transform.move(position);
    }

    public QuaternionCamera(Component cameraComponent, TransformProperty transform, ProjectionMatrix projection) {
        this(cameraComponent, new Vector3f(), transform, projection);
    }

    @Override
    public void move(Vector3f d) {
        transform.move(d);
    }


    @Override
    public void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians) {
        transform.rotate(angleXInRadians, angleYInRadians, angleZInRadians);
    }

    @Override
    public void rotateLocalX(float angleInRadians) {
        transform.rotateLocalX(angleInRadians);
    }

    @Override
    public void rotateLocalY(float angleInRadians) {
        transform.rotateLocalY(angleInRadians);
    }

    @Override
    public void rotateLocalZ(float angleInRadians) {
        transform.rotateLocalZ(angleInRadians);
    }

    @Override
    public void rotateX(float angle) {
        transform.rotateX(angle);
    }

    @Override
    public void rotateY(float angle) {
        transform.rotateY(angle);
    }

    @Override
    public void rotateZ(float angle) {
        transform.rotateZ(angle);
    }

    private Matrix4f cameraMatrix = new Matrix4f();
    private Matrix4f rotationMatrix = new Matrix4f();
    private Vector3f cameraPos = new Vector3f();

    @Override
    public synchronized void update(int d) {
        this.cameraMatrix = Transforms.getAbsoluteTransform(cameraComponent).invert();
        this.rotationMatrix = Transforms.getAbsoluteRotation(cameraComponent).get(tempRotation).invert();
        Transforms.getAbsolutePosition(cameraComponent, cameraPos);
    }


    @Override
    public synchronized Vector3f getPosition(Vector3f dest) {
        return dest.set(cameraPos);
    }

    @Override
    public Quaternionf getNonrealtiveRotation() {
        return Transforms.getAbsoluteRotation(cameraComponent);
    }

    @Override
    public synchronized Matrix4f getCameraMatrix() {
        return cameraMatrix;
    }

    public synchronized TransformProperty getTransform() {
        return transform;
    }

    @Override
    public synchronized ProjectionMatrix getProjectionMatrix() {
        return projection;
    }

    private Matrix4f tempRotation = new Matrix4f();

    @Override
    public synchronized Matrix4f getRotationMatrix() {
        return rotationMatrix;
    }

    private Vector3f forwardVector = new Vector3f();

    @Override
    public Vector3f getForwardVector() {
        return Transforms.getAbsoluteRotation(cameraComponent).positiveZ(forwardVector).negate();
    }

    private Vector3f rightVector = new Vector3f();

    @Override
    public Vector3f getRightVector() {
        return Transforms.getAbsoluteRotation(cameraComponent).positiveX(rightVector).negate();
    }

    private Vector3f upVector = new Vector3f();

    @Override
    public Vector3f getUpVector() {
        return Transforms.getAbsoluteRotation(cameraComponent).positiveY(upVector).negate();
    }

}
