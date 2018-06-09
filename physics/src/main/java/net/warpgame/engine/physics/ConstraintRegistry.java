package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btPoint2PointConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint;
import net.warpgame.engine.physics.constraints.BallConstraint;
import net.warpgame.engine.physics.constraints.Constraint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Hubertus
 * Created 07.10.2017
 */
public class ConstraintRegistry {


    private Set<Constraint> toAdd = new HashSet<>();
    private Set<Integer> toRemove = new HashSet<>();
    private Map<Integer, btTypedConstraint> constraintMap = new HashMap<>();

    synchronized void addConstraint(Constraint constraintDefinition) {
        toAdd.add(constraintDefinition);
    }

    synchronized void removeConstraint(int id) {
        toRemove.add(id);
    }

    synchronized void update(btDynamicsWorld world) {
        addConstraints(world);
        removeConstraints(world);
    }

    private void addConstraints(btDynamicsWorld world) {
        for (Constraint c : toAdd) {
            switch (c.getType()) {
                case Constraint.BALL_CONSTRAINT:
                    BallConstraint ballConstraint = (BallConstraint) c;
                    btPoint2PointConstraint bConstraint = new btPoint2PointConstraint(
                            ballConstraint.getBody1().getBulletRigidBody(),
                            ballConstraint.getBody2().getBulletRigidBody(),
                            ballConstraint.getPivot1(),
                            ballConstraint.getPivot2());
                    addToWorld(world, bConstraint, c.getID());
                    break;
                case Constraint.CONE_CONSTRAINT:
                    //TODO implement support for CONE_CONSTRAINT
                    break;
                case Constraint.FIXED_CONSTRAINT:
                    //TODO implement support for FIXED_CONSTRAINT
                    break;
                case Constraint.GENERIC_CONSTRAINT:
                    //TODO implement support fot GENERIC_CONSTRAINT
                    break;
                case Constraint.HINGE_CONSTRAINT:
                    //TODO implement support for HINGE_CONSTRAINT
                    break;
                case Constraint.SLIDER_CONSTRAINT:
                    //TODO implement support for SLIDER_CONSTRAINT
                    break;
            }
        }
    }

    private void addToWorld(btDynamicsWorld world, btTypedConstraint constraint, int id) {
        constraintMap.put(id, constraint);
        world.addConstraint(constraint);
    }

    private void removeConstraints(btDynamicsWorld world) {
        for (int id : toRemove) {
            btTypedConstraint constraint = constraintMap.get(id);
            if (constraint != null) {
                world.removeConstraint(constraint);
                constraintMap.remove(id);
            }
        }
    }

}
