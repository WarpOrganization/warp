package pl.warp.engine.common.transform;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 14
 */
public class Transforms {
    public static Matrix4f getAbsoluteTransform(Component component) {
        Matrix4f transformMatrix = new Matrix4f().identity();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            applyTransform(transformMatrix, component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME));
        if (component.hasParent()) {
            Matrix4f parentFullTransform = getAbsoluteTransform(component.getParent());
            return parentFullTransform.mul(transformMatrix);
        } else return transformMatrix;
    }

    private static void applyTransform(Matrix4f matrix, TransformProperty transform) {
        matrix.translate(transform.getTranslation());
        matrix.scale(transform.getScale());
        matrix.rotate(transform.getRotation());
    }

    public static Quaternionf getAbsoluteRotation(Component component) {
        Quaternionf rotation = new Quaternionf();
        if (component.hasProperty(TransformProperty.TRANSFORM_PROPERTY_NAME)) {
            TransformProperty property = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            rotation.set(property.getRotation());
        }
        if (component.hasParent()) {
            Quaternionf parentRotation = getAbsoluteRotation(component.getParent());
            return parentRotation.mul(rotation);
        } else return rotation;
    }

    public static Vector3f getAbsolutePosition(Component component, Vector3f dest) {
        dest.zero();
        for (Component comp = component; comp != null; comp = comp.getParent()) {
            if (comp.hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME)) {
                TransformProperty property = comp.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                dest.rotate(property.getRotation());
                dest.mul(property.getScale());
                dest.add(property.getTranslation());
            }
        }
        return dest;
    }





}

