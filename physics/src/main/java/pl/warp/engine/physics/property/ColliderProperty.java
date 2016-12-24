package pl.warp.engine.physics.property;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.physics.collider.Collider;

/**
 * @author Hubertus
 *         Created 7/3/16
 */
public class ColliderProperty extends Property<Component> {
    public static final String COLLIDER_PROPERTY_NAME = "collider";

    private Collider collider;

    public ColliderProperty(Component owner, Collider collider) {
        super(owner, COLLIDER_PROPERTY_NAME);
        this.collider = collider;
    }

    public Collider getCollider() {
        return collider;
    }
}
