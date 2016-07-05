package pl.warp.engine.core.scene.properties;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
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

    public TransformProperty(Component owner) {
        super(owner, TRANSFORM_PROPERTY_NAME);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void scale(Vector3f value){
        this.scale.mul(value);
    }

    public void rotate(float xAngleInRadians, float yAngleInRadians, float zAngleInRadians) {
        rotation.rotateLocal(xAngleInRadians, yAngleInRadians, zAngleInRadians);
    }

    public void rotateX(float angleInRadians) {
        rotation.rotateLocalX(angleInRadians);
    }

    public void rotateY(float angleInRadians) {
        rotation.rotateLocalY(angleInRadians);
    }

    public void rotateZ(float angleInRadians) {
        rotation.rotateLocalZ(angleInRadians);
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void move(Vector3f translation) {
        this.translation.add(translation);
    }
}
