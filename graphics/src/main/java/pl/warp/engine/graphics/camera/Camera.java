package pl.warp.engine.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.SceneComponent;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public abstract class Camera extends SceneComponent {

    public Camera(Component parent) {
        super(parent);
    }

    public abstract void move(Vector3f v);

    public abstract void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians);

    public abstract void rotateLocalX(float angleInRadians);

    public abstract void rotateLocalY(float angleInRadians);

    public abstract void rotateLocalZ(float angleInRadians);

    public abstract Vector3f getPosition(Vector3f dest);

    public abstract Matrix4f getCameraMatrix();

    public abstract ProjectionMatrix getProjectionMatrix();

    public abstract Matrix4f getRotationMatrix();

    public abstract Vector3f getForwardVector();

    public abstract Vector3f getRightVector();

    public abstract Vector3f getUpVector();
}
