package pl.warp.test;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class BarrelControlProperty extends Property {
    public static final String BARREL_CONTROL_PROPERTY = "barrelControl";

    private  final float elevationSpeed;
    private final float elevationMAX;
    private final float elevationMIN;

    public BarrelControlProperty(float elevationSpeed, float elevationMAX, float elevationMIN) {
        super(BARREL_CONTROL_PROPERTY);
        this.elevationSpeed = elevationSpeed;
        this.elevationMAX = elevationMAX;
        this.elevationMIN = elevationMIN;
    }

    public float getElevationSpeed() {
        return elevationSpeed;
    }

    public float getElevationMAX() {
        return elevationMAX;
    }

    public float getElevationMIN() {
        return elevationMIN;
    }
}
