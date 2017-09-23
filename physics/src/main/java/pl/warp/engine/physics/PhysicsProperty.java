package pl.warp.engine.physics;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Hubertus
 * Created 23.09.2017
 */
public class PhysicsProperty extends Property<Component> {
    public static final String PHYSICS_PROPERTY_NAME = "physicsProperty";

    public PhysicsProperty(){
        super(PHYSICS_PROPERTY_NAME);
    }
}
