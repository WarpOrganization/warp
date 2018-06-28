package net.warpgame.engine.graphics.animation;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Jaca777
 * Created 2018-06-24 at 15
 */
public class JointTransform {
    private Vector3f position;
    private Quaternionf rotation;

    public JointTransform(Vector3f position, Quaternionf rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public JointTransform() {
        this(new Vector3f(), new Quaternionf());
    }

    private Matrix4f localTransformMatrix;
    private Matrix4f tempMatrix = new Matrix4f();

    public Matrix4f getLocalTransform() {
        localTransformMatrix = new Matrix4f();
        localTransformMatrix.translate(position);
        Matrix4f rotationMatrix = rotation.get(tempMatrix);
        localTransformMatrix.mul(rotationMatrix);
        return localTransformMatrix;
    }

    public void setInterpolate(JointTransform transform1, JointTransform transform2, float t){
        transform1.position.lerp(transform2.position, t, position);
        transform1.rotation.nlerp(transform2.rotation, t, rotation);
    }
}
