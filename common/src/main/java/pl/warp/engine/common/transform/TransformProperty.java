package pl.warp.engine.common.transform;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 *         Created 2016-07-04 at 18
 */
public class TransformProperty extends Property {

    public static final String NAME = "transform";

    private Vector3f translation = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private Vector3f scale = new Vector3f().set(1);

    public TransformProperty() {
        super(NAME);
    }

    protected TransformProperty(Vector3f translation, Quaternionf rotation, Vector3f scale) {
        super(NAME);
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public synchronized Vector3f getScale() {
        return scale;
    }

    public synchronized void scale(Vector3f value){
        this.scale.mul(value);
    }

    public synchronized void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public synchronized void rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        rotation.rotate(xAngleInRadians, yAngleInRadians, zAngleInRadians);
    }

    public synchronized void rotateLocalX(float angleInRadians) {
        rotation.rotateLocalX(angleInRadians);
    }

    public synchronized void rotateLocalY(float angleInRadians) {
        rotation.rotateLocalY(angleInRadians);
    }

    public synchronized void rotateLocalZ(float angleInRadians) {
        rotation.rotateLocalZ(angleInRadians);
    }

    public synchronized Quaternionf rotateX(float angle) {
        return rotation.rotateX(angle);
    }

    public synchronized Quaternionf rotateY(float angle) {
        return rotation.rotateY(angle);
    }

    public synchronized Quaternionf rotateZ(float angle) {
        return rotation.rotateZ(angle);
    }

    public synchronized Quaternionf getRotation() {
        return rotation;
    }

    public synchronized Vector3f getTranslation() {
        return translation;
    }

    public synchronized void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public synchronized void move(Vector3f movement) {
        this.translation.add(movement);
    }

    public synchronized void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation);
    }
}
