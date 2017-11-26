package pl.warp.engine.physics.constraints;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 04.10.2017
 */
public class BallConstraint extends Constraint {

    private final btRigidBody body1;
    private final btRigidBody body2;
    private final Vector3 pivot1;
    private final Vector3 pivot2;

    public BallConstraint(btRigidBody body1, btRigidBody body2, Vector3f pivot1, Vector3f pivot2) {

        this.pivot1 = new Vector3(pivot1.x, pivot1.y, pivot1.z);
        this.pivot2 = new Vector3(pivot2.x, pivot2.y, pivot2.z);
        this.body1 = body1;
        this.body2 = body2;
    }

    @Override
    public int getType() {
        return Constraint.BALL_CONSTRAINT;
    }

    public btRigidBody getBody1() {
        return body1;
    }

    public btRigidBody getBody2() {
        return body2;
    }

    public Vector3 getPivot1() {
        return pivot1;
    }

    public Vector3 getPivot2() {
        return pivot2;
    }
}
