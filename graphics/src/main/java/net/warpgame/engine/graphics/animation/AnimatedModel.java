package net.warpgame.engine.graphics.animation;

import net.warpgame.engine.graphics.mesh.StaticMesh;
import org.joml.Matrix4f;

/**
 * @author Jaca777
 * Created 2018-06-09 at 15
 */
public class AnimatedModel {
    private StaticMesh mesh;
    private Joint rootJoint;
    private int jointCount;

    private Animator animator;

    public AnimatedModel(StaticMesh mesh, Joint root, int jointCount) {
        this.mesh = mesh;
        this.rootJoint = root;
        this.jointCount = jointCount;
        this.animator = new Animator();
        root.calculateInverseBindTransform(new Matrix4f());
    }

    public StaticMesh getMesh() {
        return mesh;
    }

    public Joint getRootJoint() {
        return rootJoint;
    }

    public int getJointCount() {
        return jointCount;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void destroy() {
        mesh.destroy();
    }

    public Matrix4f[] getJointTransforms() {
        Matrix4f[] jointMatrices = new Matrix4f[jointCount];
        addJointToArray(rootJoint, jointMatrices);
        return jointMatrices;
    }

    private void addJointToArray(Joint parentJoint, Matrix4f[] jointMatrices) {
        jointMatrices[parentJoint.getIndex()] = parentJoint.getAnimationTransform();
        for (Joint joint : parentJoint.getChildren()) {
            addJointToArray(joint, jointMatrices);
        }
    }
}
