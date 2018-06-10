package net.warpgame.engine.physics.constraints;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.dynamics.btPoint2PointConstraint;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.physics.PhysicsService;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 04.10.2017
 */
public class BallConstraint extends Constraint {

    private final Vector3 pivot1;
    private final Vector3 pivot2;

    public BallConstraint(FullPhysicsProperty property1,
                          FullPhysicsProperty property2,
                          Vector3f pivot1,
                          Vector3f pivot2) {
        super(property1, property2);
        this.pivot1 = new Vector3(pivot1.x, pivot1.y, pivot1.z);
        this.pivot2 = new Vector3(pivot2.x, pivot2.y, pivot2.z);
        bulletConstraint = new btPoint2PointConstraint(
                property1.getRigidBody().getBulletRigidBody(),
                property2.getRigidBody().getBulletRigidBody(),
                new Vector3(pivot1.x, pivot1.y, pivot1.z),
                new Vector3(pivot2.x, pivot1.y, pivot2.z));
    }

    @Override
    public int getType() {
        return Constraint.BALL_CONSTRAINT;
    }

    public Vector3 getPivot1() {
        return pivot1;
    }

    public Vector3 getPivot2() {
        return pivot2;
    }

    /**
     * Creates Point-to-Point constraint between two rigid bodies and automagically adds it to both properties and world.
     * No further action is required.
     * @param property1 physics property of first body
     * @param property2 physics property of second body
     * @param pivot1 contact point relative to first rigid body
     * @param pivot2 contact point relative to second rigid body
     * @return constructed constraint containing unique constraint id
     * @see net.warpgame.engine.physics.RigidBody#setOffset(Vector3f, Quaternionf)
     */
    public static BallConstraint createConstraint(
            FullPhysicsProperty property1,
            FullPhysicsProperty property2,
            Vector3f pivot1,
            Vector3f pivot2) {
        BallConstraint constraint = new BallConstraint(property1, property2, pivot1, pivot2);
        property1
                .getOwner()
                .getContext()
                .getLoadedContext()
                .findOne(PhysicsService.class)
                .get()
                .addConstraint(constraint);
        return constraint;
    }
}
