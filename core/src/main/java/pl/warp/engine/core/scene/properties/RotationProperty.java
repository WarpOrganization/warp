package pl.warp.engine.core.scene.properties;

import org.joml.Quaternionf;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

public class RotationProperty extends Property<Component> {

    public static final String ROTATION_PROPERTY_NAME = "rotation";

    private Quaternionf quaternion;

    public RotationProperty(Component owner, float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        super(owner);
        this.quaternion = new Quaternionf(xAngleInRadians, yAngleInRadians, zAngleInRadians);
    }


    public void rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        quaternion.rotate(xAngleInRadians, yAngleInRadians, zAngleInRadians);
    }

    public void rotateX(float angleInRadians) {
        quaternion.rotateX(angleInRadians);
    }

    public void rotateY(float angleInRadians) {
        quaternion.rotateY(angleInRadians);
    }

    public void rotateZ(float angleInRadians) {
        quaternion.rotateZ(angleInRadians);
    }

    public void rotate(RotationProperty rotation) {
        this.quaternion.add(rotation.getQuaternion());
    }


    public Quaternionf getQuaternion() {
        return quaternion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotationProperty that = (RotationProperty) o;
        return Objects.equals(getQuaternion(), that.getQuaternion());
    }

    @Override
    public int hashCode() {
        return getQuaternion().hashCode();
    }
}
