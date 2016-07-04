package pl.warp.engine.physics.property;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by hubertus on 7/4/16.
 */

public class TransformChangeProperty extends Property<Component> {

    public static final String TRANSFORM_CHANGE_PROPERTY_NAME = "transformChange";

    private Vector3f acceleration;
    private float xAngleChange;
    private float yAngleChange;
    private float zAngleChange;

    public TransformChangeProperty(Component owner) {
        super(owner, TRANSFORM_CHANGE_PROPERTY_NAME);
        acceleration = new Vector3f();
        xAngleChange = 0;
        yAngleChange = 0;
        zAngleChange = 0;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration.set(acceleration);
    }

    public void accelerate(Vector3f value) {
        acceleration.add(value);
    }

    public float getzAngleChange() {
        return zAngleChange;
    }

    public float getyAngleChange() {
        return yAngleChange;
    }

    public float getxAngleChange() {
        return xAngleChange;
    }

    public void setxAngleChange(float xAngleChange) {
        this.xAngleChange = xAngleChange;
    }

    public void setyAngleChange(float yAngleChange) {
        this.yAngleChange = yAngleChange;
    }

    public void setzAngleChange(float zAngleChange) {
        this.zAngleChange = zAngleChange;
    }

    public void rotateX(float value) {
        xAngleChange += value;
    }

    public void rotateY(float value) {
        yAngleChange += value;
    }

    public void rotateZ(float value) {
        zAngleChange += value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransformChangeProperty that = (TransformChangeProperty) o;
        return Float.compare(that.xAngleChange, xAngleChange) == 0 &&
                Float.compare(that.yAngleChange, yAngleChange) == 0 &&
                Float.compare(that.zAngleChange, zAngleChange) == 0 &&
                Objects.equals(acceleration, that.acceleration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acceleration, xAngleChange, yAngleChange, zAngleChange);
    }
}
