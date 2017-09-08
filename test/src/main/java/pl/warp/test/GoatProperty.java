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
    private float brakingForce;
    private float arrowKeysRotationSpeed;

    public GoatProperty(float movementSpeed, float rotationSpeed, float brakingForce, float arrowKeysRotationSpeed) {
        super(GOAT_PROPERTY_NAME);
        this.movementSpeed = movementSpeed;
        this.rotationSpeed = rotationSpeed;
        this.brakingForce = brakingForce;
        this.arrowKeysRotationSpeed = arrowKeysRotationSpeed;
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

    public float getBrakingForce() {
        return brakingForce;
    }

    public GoatProperty setBrakingForce(float brakingForce) {
        this.brakingForce = brakingForce;
        return this;
    }

    public float getArrowKeysRotationSpeed() {
        return arrowKeysRotationSpeed;
    }

    public GoatProperty setArrowKeysRotationSpeed(float arrowKeysRotationSpeed) {
        this.arrowKeysRotationSpeed = arrowKeysRotationSpeed;
        return this;
    }
}
