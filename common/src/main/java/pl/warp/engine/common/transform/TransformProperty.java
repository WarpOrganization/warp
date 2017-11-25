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

    public synchronized TransformProperty scale(Vector3f value){
        this.scale.mul(value);
        return this;
    }

    public synchronized void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public synchronized TransformProperty rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        rotation.rotate(xAngleInRadians, yAngleInRadians, zAngleInRadians);
        return this;
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

    public synchronized TransformProperty rotateX(float angle) {
         rotation.rotateX(angle);
         return this;
    }

    public synchronized TransformProperty rotateY(float angle) {
         rotation.rotateY(angle);
         return this;
    }

    public synchronized TransformProperty rotateZ(float angle) {
         rotation.rotateZ(angle);
         return this;
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

    public TransformProperty move(Vector3f movement) {
        this.translation.add(movement);
        return this;
    }

    public synchronized TransformProperty setRotation(Quaternionf rotation) {
        this.rotation.set(rotation);
        return this;
    }
}
