package pl.warp.test;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class TurretProperty extends Property {
    public static final String TURRET_PROPERTY_NAME = "turretProperty";

    private float rotationSpeed;

    public TurretProperty(float rotationSpeed) {
        super(TURRET_PROPERTY_NAME);
        this.rotationSpeed = rotationSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public TurretProperty setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        return this;
    }
}
