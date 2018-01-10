package net.warpgame.engine.common.physics;

import net.warpgame.engine.core.property.Property;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 07.01.2018
 */
public class SimplePhysicsProperty extends Property implements PhysicsInterface {

    public static final String NAME = "simplePhysicsProperty";

    private Vector3f velocity = new Vector3f();
    private Quaternionf angularVelocity = new Quaternionf();
    private float mass;
    private Matrix3f inertia;

    public SimplePhysicsProperty() {
        super(NAME);
    }

    public Quaternionf getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(Quaternionf angularVelocity) {
        this.angularVelocity.set(angularVelocity);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    @Override
    public void applyCentralForce(Vector3f force) {
        velocity.add(force.x / mass, force.y / mass, force.z / mass);
    }

    @Override
    public void applyTorque(Vector3f torque) {
        angularVelocity.set(0, 0, 0, 0.5f);
        angularVelocity.rotate(torque.x, torque.y, torque.z);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Matrix3f getInertia() {
        return inertia;
    }

    public void setInertia(Matrix3f inertia) {
        this.inertia.set(inertia);
    }
}
