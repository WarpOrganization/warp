package net.warpgame.engine.physics;

import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.PropertyNotPresentException;
import net.warpgame.engine.physics.constraints.Constraint;

/**
 * @author Hubertus
 * Created 22.09.2017
 */
@Service
public class PhysicsManager {

    private PhysicsTask physicsTask;
    private IDDispatcher constraintIDDispatcher = new IDDispatcher();

    public PhysicsManager(PhysicsTask physicsTask) {
        this.physicsTask = physicsTask;
    }


    public void addRigidBody(Component component) {
        if (!component.hasEnabledProperty(TransformProperty.NAME))
            throw new PropertyNotPresentException(TransformProperty.NAME);

        if (!component.hasEnabledProperty(PhysicsProperty.NAME))
            throw new PropertyNotPresentException(PhysicsProperty.NAME);
        physicsTask.getRigidBodyRegistry().addRigidBody(component);
    }

    public void removeRigidBody(Component component) {
        if (!component.hasEnabledProperty(TransformProperty.NAME))
            throw new PropertyNotPresentException(TransformProperty.NAME);

        if (!component.hasEnabledProperty(PhysicsProperty.NAME))
            throw new PropertyNotPresentException(PhysicsProperty.NAME);
        physicsTask.getRigidBodyRegistry().removeRigidBody(component);
    }

    public void addConstraint(Constraint constraintDefinition) {
        constraintDefinition.setID(constraintIDDispatcher.getNextID());
        physicsTask.getConstraintRegistry().addConstraint(constraintDefinition);
    }

    public void removeConstraint(int id) {
        physicsTask.getConstraintRegistry().removeConstraint(id);
    }

}
