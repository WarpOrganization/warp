package net.warpgame.engine.physics;

import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.PropertyNotPresentException;
import net.warpgame.engine.physics.constraints.Constraint;

/**
 * @author Hubertus
 * Created 22.09.2017
 */
@Service
@Profile("fullPhysics")
public class PhysicsManager {

    private PhysicsTask physicsTask;
    private IDDispatcher constraintIDDispatcher = new IDDispatcher();

    public PhysicsManager(PhysicsTask physicsTask) {
        this.physicsTask = physicsTask;
    }


    public void addRigidBody(Component component) {
        if (!component.hasEnabledProperty(Property.getTypeId(TransformProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(TransformProperty.class));

        if (!component.hasEnabledProperty(Property.getTypeId(FullPhysicsProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(FullPhysicsProperty.class));
        physicsTask.getRigidBodyRegistry().addRigidBody(component);
    }

    public void removeRigidBody(Component component) {
        if (!component.hasEnabledProperty(Property.getTypeId(TransformProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(TransformProperty.class));

        if (!component.hasEnabledProperty(Property.getTypeId(FullPhysicsProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(FullPhysicsProperty.class));
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
