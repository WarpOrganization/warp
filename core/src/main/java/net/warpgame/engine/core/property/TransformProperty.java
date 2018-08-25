package net.warpgame.engine.core.property;

import org.joml.*;

/**
 * @author Jaca777
 * Created 2016-07-04 at 18
 */
public class TransformProperty extends Property {

    private Vector3f translation;
    private Quaternionf rotation;
    private Vector3f scale;

    private boolean dirty;
    private Matrix4f transformCache;
    private Matrix3f rotationCache;

    protected TransformProperty(Vector3fc translation, Quaternionfc rotation, Vector3fc scale) {
        this.translation = new Vector3f(translation);
        this.rotation = new Quaternionf().set(rotation);
        this.scale = new Vector3f(scale);
        dirty = true;
        transformCache = new Matrix4f();
        rotationCache = new Matrix3f();
    }

    public TransformProperty() {
        translation = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f().set(1);
        dirty = true;
        transformCache = new Matrix4f();
        rotationCache = new Matrix3f();
    }

    public synchronized Vector3fc getScale() {
        return scale;
    }

    public synchronized TransformProperty scale(Vector3fc value) {
        this.dirty = true;
        this.scale.mul(value);
        return this;
    }

    public synchronized void setScale(Vector3fc scale) {
        this.dirty = true;
        this.scale.set(scale);
    }

    public synchronized TransformProperty rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        this.dirty = true;
        rotation.rotateXYZ(xAngleInRadians, yAngleInRadians, zAngleInRadians);
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

    public synchronized Quaternionfc getRotation() {
        return rotation;
    }

    public synchronized Vector3fc getTranslation() {
        return translation;
    }

    public synchronized Vector3f getTranslation(Vector3f out) {
        return out.set(translation);
    }

    public synchronized void setTranslation(Vector3fc translation) {
        this.dirty = true;
        this.translation.set(translation);
    }

    public TransformProperty move(float x, float y, float z) {
        return move(new Vector3f(x, y, z));
    }

    public TransformProperty move(Vector3fc movement) {
        this.dirty = true;
        this.translation.add(movement);
        return this;
    }

    public synchronized TransformProperty setRotation(Quaternionfc rotation) {
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

    public Matrix4fc getCachedNonrelativeTransform() {
        return transformCache;
    }

    public Matrix3fc getCachedNonrelativeRotation() {
        return rotationCache;
    }
}
