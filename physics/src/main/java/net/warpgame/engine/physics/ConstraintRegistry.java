package net.warpgame.engine.physics;

import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
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
    private Map<Integer, Constraint> constraintMap = new HashMap<>();

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
            constraintMap.put(c.getID(), c);
            world.addConstraint(c.getBulletConstraint());
        }
    }

    private void removeConstraints(btDynamicsWorld world) {
        for (int id : toRemove) {
            Constraint constraint = constraintMap.get(id);
            if (constraint != null) {
                world.removeConstraint(constraint.getBulletConstraint());
                constraintMap.remove(id);
            }
        }
    }

    Constraint getConstraint(int constraintId) {
        return constraintMap.get(constraintId);
    }
}
