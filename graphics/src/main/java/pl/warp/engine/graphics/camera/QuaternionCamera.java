package pl.warp.engine.graphics.camera;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 20
 */
public class QuaternionCamera extends Camera {

    private Vector3f position;
    private Quaternionf rotation = new Quaternionf();
    private ProjectionMatrix projection;

    public QuaternionCamera(Component parent, Vector3f position, ProjectionMatrix projection) {
        super(parent);
        this.position = position;
        this.projection = projection;
    }

    public QuaternionCamera(Component parent, ProjectionMatrix projection) {
        this(parent, new Vector3f(), projection);
    }

    @Override
    public void move(Vector3f d) {
        position.add(d);
    }

    @Override
    public void move(float dx, float dy, float dz) {
        position.add(dx, dy, dz);
    }

    @Override
    public void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians) {
        rotation.rotate(angleXInRadians, angleYInRadians, angleZInRadians);
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    private Matrix4f tempMatrix = new Matrix4f();

    @Override
    public Matrix4f getCameraMatrix() {
        return rotation.get(tempMatrix);
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection.getMatrix();
    }

    private static final Vector3f forwardVector = new Vector3f(0, 0, 1);

    @Override
    public Vector3f getDirectionVector() {
        Matrix3f cameraMatrix3x3 = new Matrix3f();
        return forwardVector.mul(getCameraMatrix().get3x3(cameraMatrix3x3));
    }

    public Quaternionf getRotation() {
        return rotation;
    }

}
