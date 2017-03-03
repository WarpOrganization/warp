package pl.warp.engine.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public interface Camera {

    void move(Vector3f v);

    void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians);

    void rotateLocalX(float angleInRadians);

    void rotateLocalY(float angleInRadians);

    void rotateLocalZ(float angleInRadians);

    void rotateX(float angle);

    void rotateY(float angle);

    void rotateZ(float angle);

    void update();

    Vector3f getPosition(Vector3f dest);

    Quaternionf getNonrealtiveRotation();

    Matrix4f getCameraMatrix();

    ProjectionMatrix getProjectionMatrix();

    Matrix4f getRotationMatrix();

    Vector3f getForwardVector();

    Vector3f getRightVector();

    Vector3f getUpVector();

}
