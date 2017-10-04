package pl.warp.test;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class GoatProperty extends Property {
    public static final String GOAT_PROPERTY_NAME = "goatProperty";

    private float movementSpeed;
    private float rotationSpeed;
    private float linearDamping;
    private float arrowKeysRotationSpeed;
    private float angularDamping;

    public GoatProperty(float movementSpeed, float rotationSpeed, float linearDamping, float arrowKeysRotationSpeed, float angualrDamping) {
        super(GOAT_PROPERTY_NAME);
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.linearDamping = linearDamping;
        this.arrowKeysRotationSpeed = arrowKeysRotationSpeed;
        this.angularDamping = angualrDamping;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public GoatProperty setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
        return this;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public GoatProperty setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        return this;
    }

    public float getLinearDamping() {
        return linearDamping;
    }

    public GoatProperty setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public float getArrowKeysRotationSpeed() {
        return arrowKeysRotationSpeed;
    }

    public GoatProperty setArrowKeysRotationSpeed(float arrowKeysRotationSpeed) {
        this.arrowKeysRotationSpeed = arrowKeysRotationSpeed;
        return this;
    }

    public float getAngularDamping() {
        return angularDamping;
    }

    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }
}
