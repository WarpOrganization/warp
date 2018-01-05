package net.warpgame.engine.graphics.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.execution.task.update.Updatable;
import net.warpgame.engine.graphics.utility.projection.ProjectionMatrix;


/**
 * @author Jaca777
 *         Created 2016-07-01 at 12
 */
public interface Camera extends Updatable {

    Component getCameraComponent();

    void move(Vector3f v);

    void rotate(float angleXInRadians, float angleYInRadians, float angleZInRadians);

    void rotateLocalX(float angleInRadians);

    void rotateLocalY(float angleInRadians);

    void rotateLocalZ(float angleInRadians);

    void rotateX(float angle);

    void rotateY(float angle);

    void rotateZ(float angle);

    void update(int delta);

    Vector3f getPosition(Vector3f dest);

    Matrix4f getCameraMatrix();

    ProjectionMatrix getProjectionMatrix();

    Matrix4f getRotationMatrix();

    Vector3f getForwardVector();

    Vector3f getRightVector();

    Vector3f getUpVector();

}
