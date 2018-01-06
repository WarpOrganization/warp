package net.warpgame.engine.common.transform;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2016-07-04 at 18
 */
public class TransformProperty extends Property {

    public static final String NAME = "transform";

    private Vector3f translation = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private Vector3f scale = new Vector3f().set(1);

    private boolean dirty = true;
    private Matrix4f transformCache = new Matrix4f();
    private Matrix3f rotationCache = new Matrix3f();

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

    public synchronized TransformProperty scale(Vector3f value) {
        this.dirty = true;
        this.scale.mul(value);
        return this;
    }

    public synchronized void setScale(Vector3f scale) {
        this.dirty = true;
        this.scale = scale;
    }

    public synchronized TransformProperty rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        this.dirty = true;
        rotation.rotate(xAngleInRadians, yAngleInRadians, zAngleInRadians);
        return this;
    }

    public synchronized void rotateLocalX(float angleInRadians) {
        this.dirty = true;
        rotation.rotateLocalX(angleInRadians);
    }

    public synchronized void rotateLocalY(float angleInRadians) {
        this.dirty = true;
        rotation.rotateLocalY(angleInRadians);
    }

    public synchronized void rotateLocalZ(float angleInRadians) {
        this.dirty = true;
        rotation.rotateLocalZ(angleInRadians);
    }

    public synchronized TransformProperty rotateX(float angle) {
        this.dirty = true;
        rotation.rotateX(angle);
        return this;
    }

    public synchronized TransformProperty rotateY(float angle) {
        this.dirty = true;
        rotation.rotateY(angle);
        return this;
    }

    public synchronized TransformProperty rotateZ(float angle) {
        this.dirty = true;
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
        this.dirty = true;
        this.translation.set(translation);
    }

    public TransformProperty move(Vector3f movement) {
        this.dirty = true;
        this.translation.add(movement);
        return this;
    }

    public synchronized TransformProperty setRotation(Quaternionf rotation) {
        this.dirty = true;
        this.rotation.set(rotation);
        return this;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void updateCaches(Matrix4f transformMatrix, Matrix3f rotationMatrix) {
        this.transformCache.set(transformMatrix);
        this.rotationCache.set(rotationMatrix);
        this.dirty = false;
    }

    public Matrix4f getTransformCache() {
        return transformCache;
    }

    public Matrix3f getRotationCache() {
        return rotationCache;
    }
}
