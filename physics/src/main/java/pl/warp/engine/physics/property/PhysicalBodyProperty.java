package pl.warp.engine.physics.property;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.physics.property.logic.PhysicalBodyLogic;

import java.util.Objects;

/**
 * Created by hubertus on 7/4/16.
 */

public class PhysicalBodyProperty extends Property<Component> {

    public static final String PHYSICAL_BODY_PROPERTY_NAME = "physicalBody";

    private Vector3f force;
    private Vector3f torque;
    private Vector3f speed;
    private float mass;

    private PhysicalBodyLogic logic;

    public PhysicalBodyProperty(Component owner, float mass) {
        super(owner, PHYSICAL_BODY_PROPERTY_NAME);
        force = new Vector3f();
        torque = new Vector3f();
        speed = new Vector3f();
        this.mass = mass;

        logic = new PhysicalBodyLogic(this);
    }

    public Vector3f getForce() {
        return force;
    }

    public void setForce(Vector3f force) {
        this.force.set(force);
    }

    public void accelerate(Vector3f value) {
        force.add(value);
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

    public void addSpeed(Vector3f acceleration) {
        speed.add(acceleration);
    }

    public Vector3f getSpeed() {
        return speed;
    }

    public PhysicalBodyLogic getLogic() {
        return logic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalBodyProperty that = (PhysicalBodyProperty) o;
        return Float.compare(that.mass, mass) == 0 &&
                Objects.equals(force, that.force) &&
                Objects.equals(torque, that.torque) &&
                Objects.equals(speed, that.speed) &&
                Objects.equals(logic, that.logic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(force, torque, speed, mass, logic);
    }
}
