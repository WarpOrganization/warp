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

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, 1);

    private Vector3f position;
    private Quaternionf rotation = new Quaternionf();
    private ProjectionMatrix projection;
    private Vector3f forwardVector = new Vector3f();

    public QuaternionCamera(Component parent, Vector3f position, ProjectionMatrix projection) {
        super(parent);
        this.position = position;
        this.projection = projection;
        calcForwardVector();
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
        rotation.rotateLocal(angleXInRadians, angleYInRadians, angleZInRadians);
    }

    @Override
    public void rotateX(float angleInRadians) {
        rotation.rotateLocalX(angleInRadians);
    }

    @Override
    public void rotateY(float angleInRadians) {
        rotation.rotateLocalY(angleInRadians);
    }

    @Override
    public void rotateZ(float angleInRadians) {
        rotation.rotateLocalZ(angleInRadians);
    }


    private Matrix3f tempCamera3x3Matrix = new Matrix3f();
    private void calcForwardVector() {
        getCameraMatrix().get3x3(tempCamera3x3Matrix);
        FORWARD_VECTOR.mul(tempCamera3x3Matrix, forwardVector);
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    private Matrix4f tempMatrix = new Matrix4f();

    @Override
    public Matrix4f getCameraMatrix() {
        rotation.get(tempMatrix);
        tempMatrix.translate(position);
        return tempMatrix;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection.getMatrix();
    }

    @Override
    public Vector3f getForwardVector() {
        return forwardVector;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

}
