package pl.warp.engine.physics.property;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Hubertus
 *         Created 05.08.16
 */
public class GravityAffectedBodyProperty extends Property<Component> {

    public static final String GRAVITY_AFFECTED_BODY_PROPERTY_NAME = "gravityBodyProperty";

    private Vector3f nextTickTranslation = new Vector3f();
    private Vector3f velocity = new Vector3f();

    private boolean isStanding;

    public GravityAffectedBodyProperty(Component owner, Component gravityGenerator) {
        super(owner, GRAVITY_AFFECTED_BODY_PROPERTY_NAME);
    }

    public Vector3f getNextTickTranslation() {
        return nextTickTranslation;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setNextTickTranslation(Vector3f nextTickTranslation) {
        this.nextTickTranslation.set(nextTickTranslation);
    }

    public synchronized void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
    }

    public synchronized void addVelocity(Vector3f value) {
        this.velocity.add(value);
    }

    public synchronized void addVerticalVelocity(float value) {
        this.velocity.z += value;
    }

    public synchronized void stand() {
        this.velocity.z = 0;
        this.isStanding = true;
    }
}
