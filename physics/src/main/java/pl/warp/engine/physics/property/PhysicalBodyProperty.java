package pl.warp.engine.physics.property;

import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * @author Hubertus
 *         Created 7/4/16
 */

public class PhysicalBodyProperty extends Property<Component> {

    public static final String PHYSICAL_BODY_PROPERTY_NAME = "physicalBody";

    private Vector3f angularVelocity = new Vector3f();
    private Vector3f velocity = new Vector3f();
    private float mass;
    private float radius;
    //I'm not sure if it's a good idea or overly easy workaroud
    private float universalRotationInertia;
    private Matrix3f inertia = new Matrix3f();
    private Matrix3f inertiaRotated = new Matrix3f();

    private Vector3f nextTickTranslation = new Vector3f();
    private Vector3f nextTickRotation = new Vector3f();

    public PhysicalBodyProperty(float mass, float xLength, float yLength, float zLength) {
        super(PHYSICAL_BODY_PROPERTY_NAME);
        this.mass = mass;
        this.radius = radius;
        inertia.set((zLength * zLength + yLength * yLength) * mass / 12, 0, 0,
                0, (zLength * zLength + xLength * xLength) * mass / 12, 0,
                0, 0, (xLength * xLength + yLength * yLength) * mass / 12).invert();
        inertiaRotated.set(inertia);
        universalRotationInertia = (2f * mass / 5f) * (xLength + yLength + zLength) / 3f * (xLength + yLength + zLength) / 3f;
    }

    public void recalculateInteriaTensor(Quaternionf rotation) {
        inertia.rotate(rotation, inertiaRotated);
    }

    public Vector3f getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(Vector3f angularVelocity) {
        this.angularVelocity.set(angularVelocity);
    }

    public void addAngularVelocity(Vector3f value) {
        angularVelocity.add(value);
    }

    /**
     * location of applied torqe, relative to the center of mass, in world coordinates
     */
    public void addTorque(Vector3f torque, Vector3f location) {
        angularVelocity.add(location.cross(torque).mul(inertiaRotated));
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public synchronized void applyForce(Vector3f force) {
        velocity.add(force.div(mass));
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Matrix3f getInertia() {
        return inertia;
    }

    public Matrix3f getRotatedInertia() {
        return inertiaRotated;
    }

    public float getRadius() {
        return radius;
    }

    public Vector3f getNextTickTranslation() {
        return nextTickTranslation;
    }

    public void setNextTickTranslation(Vector3f nextTickTranslation) {
        this.nextTickTranslation.set(nextTickTranslation);
    }

    public Vector3f getNextTickRotation() {
        return nextTickRotation;
    }

    public void setNextTickRotation(Vector3f nextTickRotation) {
        this.nextTickRotation.set(nextTickRotation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalBodyProperty that = (PhysicalBodyProperty) o;
        return Float.compare(that.mass, mass) == 0 &&
                Float.compare(that.radius, radius) == 0 &&
                Objects.equals(that.inertia, inertia) &&
                Objects.equals(angularVelocity, that.angularVelocity) &&
                Objects.equals(velocity, that.velocity) &&
                Objects.equals(nextTickTranslation, that.nextTickTranslation) &&
                Objects.equals(nextTickRotation, that.nextTickRotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(angularVelocity, velocity, mass, radius, inertia, nextTickTranslation, nextTickRotation);
    }

    public float getUniversalRotationInertia() {
        return universalRotationInertia;
    }

    public void setUniversalRotationInertia(float universalRotationInertia) {
        this.universalRotationInertia = universalRotationInertia;
    }
}
