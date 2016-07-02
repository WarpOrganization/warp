package pl.warp.engine.graphics.camera;

import com.google.common.collect.ImmutableList;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.math.projection.ProjectionMatrix;
import pl.warp.engine.graphics.utility.BufferTools;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 20
 */
public class QuaternionCamera extends Camera {

    private Vector3f position;
    private Quaternionf rotation = new Quaternionf();
    private ProjectionMatrix perspective;

    public QuaternionCamera(Component parent, Vector3f position, ProjectionMatrix perspective) {
        super(parent);
        this.position = position;
        this.perspective = perspective;
    }

    public QuaternionCamera(Component parent, ProjectionMatrix perspective) {
        this(parent, new Vector3f(), perspective);
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
        return perspective.getMatrix();
    }

    public Quaternionf getRotation() {
        return rotation;
    }

}
