package net.warpgame.engine.physics;

import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentDeathEvent;
import net.warpgame.engine.core.component.SimpleListener;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.PropertyNotPresentException;
import net.warpgame.engine.physics.constraints.Constraint;

/**
 * @author Hubertus
 * Created 22.09.2017
 */
@Service
@Profile("fullPhysics")
public class PhysicsService {

    private PhysicsTask physicsTask;
    private IdDispatcher constraintIdDispatcher = new IdDispatcher();

    public PhysicsService(PhysicsTask physicsTask) {
        this.physicsTask = physicsTask;
    }


    void addRigidBody(Component component) {
        if (!component.hasEnabledProperty(Property.getTypeId(TransformProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(TransformProperty.class));

        if (!component.hasEnabledProperty(Property.getTypeId(FullPhysicsProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(FullPhysicsProperty.class));
        physicsTask.getRigidBodyRegistry().addRigidBody(component);
        createDeathListener(component);
    }

    void removeRigidBody(Component component) {
        if (!component.hasEnabledProperty(Property.getTypeId(TransformProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(TransformProperty.class));

        if (!component.hasEnabledProperty(Property.getTypeId(FullPhysicsProperty.class)))
            throw new PropertyNotPresentException(Property.getTypeId(FullPhysicsProperty.class));
        physicsTask.getRigidBodyRegistry().removeRigidBody(component);
    }

    private void createDeathListener(Component component) {
        SimpleListener.createListener(
                component,
                Event.getTypeId(ComponentDeathEvent.class),
                (e) -> removeRigidBody(component)
        );
    }

    /**
     * Please use *ConstraintType*.createConstraint() to create constraints.
     * Registers constraint in ConstraintRegistry and adds it to PhysicsWorld.
     */
    public void addConstraint(Constraint constraint) {
        constraint.setID(constraintIdDispatcher.getNextID());
        physicsTask.getConstraintRegistry().addConstraint(constraint);
        constraint.getProperty1().addConstraint(constraint);
        constraint.getProperty2().addConstraint(constraint);
    }

    void removeConstraint(int constraintId) {
        Constraint constraint = physicsTask.getConstraintRegistry().getConstraint(constraintId);
        constraint.getProperty1().internalRemoveConstraint(constraint);
        constraint.getProperty2().internalRemoveConstraint(constraint);
        physicsTask.getConstraintRegistry().removeConstraint(constraintId);
    }
}
