package pl.warp.engine.core.scene.properties;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;

/**
 * @author Jaca777
 *         Created 2016-07-04 at 18
 */
public class TransformProperty extends Property{

    public static final String TRANSFORM_PROPERTY_NAME = "transform";

    private Vector3f translation = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private Vector3f scale = new Vector3f().set(1);

    public TransformProperty() {
        super(TRANSFORM_PROPERTY_NAME);
    }

    protected TransformProperty(Vector3f translation, Quaternionf rotation, Vector3f scale) {
        super(TRANSFORM_PROPERTY_NAME);
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void scale(Vector3f value){
        this.scale.mul(value);
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        rotation.rotate(xAngleInRadians, yAngleInRadians, zAngleInRadians);
    }

    public void rotateLocalX(float angleInRadians) {
        rotation.rotateLocalX(angleInRadians);
    }

    public void rotateLocalY(float angleInRadians) {
        rotation.rotateLocalY(angleInRadians);
    }

    public void rotateLocalZ(float angleInRadians) {
        rotation.rotateLocalZ(angleInRadians);
    }

    public Quaternionf rotateX(float angle) {
        return rotation.rotateX(angle);
    }

    public Quaternionf rotateY(float angle) {
        return rotation.rotateY(angle);
    }

    public Quaternionf rotateZ(float angle) {
        return rotation.rotateZ(angle);
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void move(Vector3f movement) {
        this.translation.add(movement);
    }
}
