package pl.warp.engine.graphics.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 14
 */
public class Transforms {
    public static Matrix4f getActualTransform(Component component) {
        Matrix4f transformMatrix = new Matrix4f().identity();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransform(transformMatrix, component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME));
        if (component.hasParent()) {
            Matrix4f parentFullTransform = getActualTransform(component.getParent());
            return parentFullTransform.mul(transformMatrix);
        } else return transformMatrix;
    }

    private static void applyTransform(Matrix4f matrix, TransformProperty transform) {
        matrix.translate(transform.getTranslation());
        matrix.scale(transform.getScale());
        matrix.rotate(transform.getRotation());
    }

    public static Quaternionf getActualRotation(Component component) {
        Quaternionf rotation = new Quaternionf();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME)) {
            TransformProperty property = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            rotation.set(property.getRotation());
        }
        if (component.hasParent()) {
            Quaternionf parentRotation = getActualRotation(component.getParent());
            return parentRotation.mul(rotation);
        } else return rotation;
    }
}

