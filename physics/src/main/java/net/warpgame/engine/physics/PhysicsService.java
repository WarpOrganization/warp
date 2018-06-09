package net.warpgame.engine.physics;

import net.warpgame.engine.common.transform.TransformProperty;
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
    private IDDispatcher constraintIDDispatcher = new IDDispatcher();

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

    public void addConstraint(Constraint constraintDefinition) {
        constraintDefinition.setID(constraintIDDispatcher.getNextID());
        physicsTask.getConstraintRegistry().addConstraint(constraintDefinition);
    }

    public void removeConstraint(int id) {
        physicsTask.getConstraintRegistry().removeConstraint(id);
    }

}
