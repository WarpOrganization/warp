package pl.warp.engine.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 20
 */
public class QuaternionCamera extends Camera {

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, 1);

    private ProjectionMatrix projection;
    private TransformProperty transform;

    public QuaternionCamera(Component parent, Vector3f position, ProjectionMatrix projection) {
        super(parent);
        this.projection = projection;
        this.transform = new TransformProperty(this);
        transform.move(position);
    }

    public QuaternionCamera(Component parent, ProjectionMatrix projection) {
        this(parent, new Vector3f(), projection);
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
    public void rotateX(float angleInRadians) {
        transform.rotateX(angleInRadians);
    }

    @Override
    public void rotateY(float angleInRadians) {
        transform.rotateY(angleInRadians);
    }

    @Override
    public void rotateZ(float angleInRadians) {
        transform.rotateZ(angleInRadians);
    }


    @Override
    public Vector3f getPosition() {
        return transform.getTranslation();
    }

    private Matrix4f tempMatrix = new Matrix4f();
    private Vector3f tempVector = new Vector3f();

    @Override
    public Matrix4f getCameraMatrix() {
        transform.getRotation().get(tempMatrix);
        tempMatrix.translate(transform.getTranslation().negate(tempVector));
        return tempMatrix;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection.getMatrix();
    }

    private Vector3f forwardVector = new Vector3f();

    @Override
    public Vector3f getForwardVector() {
        return transform.getRotation().positiveZ(forwardVector);
    }

    private Vector3f rightVector = new Vector3f();

    @Override
    public Vector3f getRightVector() {
        return transform.getRotation().positiveX(rightVector);
    }

    private Vector3f upVector = new Vector3f();

    @Override
    public Vector3f getUpVector() {
        return transform.getRotation().positiveY(upVector);
    }

}
