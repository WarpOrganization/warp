package pl.warp.test;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class TankGunProperty extends Property {
    public static final String TANK_GUN_PROPERTY_NAME = "tankGunProperty";

    private int reloadTime;
    private float outSpeed;
    private Component root;

    public TankGunProperty(int reloadTime, float outSpeed, Component root) {
        super(TANK_GUN_PROPERTY_NAME);
        this.reloadTime = reloadTime;
        this.outSpeed = outSpeed;
        this.root = root;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public TankGunProperty setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
        return this;
    }

    public float getOutSpeed() {
        return outSpeed;
    }

    public TankGunProperty setOutSpeed(float outSpeed) {
        this.outSpeed = outSpeed;
        return this;
    }

    public Component getRoot() {
        return root;
    }

    public TankGunProperty setRoot(Component root) {
        this.root = root;
        return this;
    }
}
