package pl.warp.engine.physics;

import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.property.PropertyNotPresentException;
import pl.warp.engine.physics.constraints.Constraint;

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
