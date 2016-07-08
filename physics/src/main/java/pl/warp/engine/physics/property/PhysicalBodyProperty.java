package pl.warp.engine.physics.property;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicalBodyProperty extends Property<Component> {

    public static final String PHYSICAL_BODY_PROPERTY_NAME = "physicalBody";

    private Vector3f torque = new Vector3f();
    private Vector3f speed = new Vector3f();
    private float mass;

    public PhysicalBodyProperty(Component owner, float mass) {
        super(owner, PHYSICAL_BODY_PROPERTY_NAME);
        this.mass = mass;
    }

    public Vector3f getTorque() {
        return torque;
    }

    public void setTorque(Vector3f torque) {
        this.torque = torque;
    }

    public void addTorque(Vector3f value) {
        torque.add(value);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector3f getSpeed() {
        return speed;
    }

    public synchronized void applyForce(Vector3f force) {
        speed.add(force.div(mass));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalBodyProperty that = (PhysicalBodyProperty) o;
        return Float.compare(that.mass, mass) == 0 &&
                Objects.equals(torque, that.torque) &&
                Objects.equals(speed, that.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(torque, speed, mass);
    }
}
