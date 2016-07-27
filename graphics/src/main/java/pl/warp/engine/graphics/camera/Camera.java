package pl.warp.engine.graphics.camera;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Actor;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;

import java.util.List;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public abstract class Camera extends Actor {

    public Camera(Component parent) {
        super(parent);
    }

    public abstract void move(Vector3f v);

    public abstract void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians);

    public abstract void rotateX(float angleInRadians);

    public abstract void rotateY(float angleInRadians);

    public abstract void rotateZ(float angleInRadians);

    public abstract Vector3f getPosition();

    public abstract Matrix4f getCameraMatrix();

    public abstract ProjectionMatrix getProjectionMatrix();

    public abstract Matrix4f getRotationMatrix();

    public abstract Vector3f getForwardVector();

    public abstract Vector3f getRightVector();

    public abstract Vector3f getUpVector();
}
