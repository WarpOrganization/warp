package net.warpgame.engine.physics.simplified;

import net.warpgame.engine.core.property.Property;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 17.06.2018
 */
public class SimplifiedPhysicsProperty extends Property {
    private Vector3f acceleration;
    private Vector3f velocity;
    private AxisAngle4f angularVelocity;
    private float mass;

    public SimplifiedPhysicsProperty(Vector3f acceleration, Vector3f velocity, AxisAngle4f angularVelocity, float mass) {
        this.velocity = new Vector3f();
        this.acceleration = new Vector3f();
        this.angularVelocity = new AxisAngle4f();
        this.acceleration.set(acceleration);
        this.velocity.set(velocity);
        this.angularVelocity.set(angularVelocity);
        this.mass = mass;
    }

    public SimplifiedPhysicsProperty(float mass) {
        this.acceleration = new Vector3f();
        this.velocity = new Vector3f();
        this.angularVelocity = new AxisAngle4f();
        this.mass = mass;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration.set(acceleration);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
    }

    public AxisAngle4f getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(AxisAngle4f angularVelocity) {
        this.angularVelocity.set(angularVelocity);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
