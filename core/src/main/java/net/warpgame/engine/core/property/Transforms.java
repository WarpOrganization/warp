package net.warpgame.engine.core.property;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.warpgame.engine.core.component.Component;

import java.util.ArrayDeque;

/**
 * @author Jaca777
 * Created 2016-07-08 at 14
 */
public class Transforms {

    private static ArrayDeque<Component> stack = new ArrayDeque<>();

    public synchronized static Matrix4f getImmediateTransform(Component component, Matrix4f dest) {
        Component firstUndirty = loadDirtyAndFetchTop(component); //see loadDirtyAndFetchCachedTop comment
        if (stack.isEmpty()) {
            TransformProperty transformProperty = component.getProperty(Property.getTypeId(TransformProperty.class));
            return dest.set(transformProperty.getCachedNonrelativeTransform());
        }
        if (firstUndirty == null) {
            dest.identity();
        } else if (!firstUndirty.hasProperty(Property.getTypeId(TransformProperty.class))) {
            dest.identity();
        } else {
            TransformProperty transformProperty = firstUndirty.getProperty(Property.getTypeId(TransformProperty.class));
            dest.set(transformProperty.getCachedNonrelativeTransform());
        }

        Component dirty;
        while ((dirty = stack.poll()) != null) {
            TransformProperty transformProperty = dirty.getProperty(Property.getTypeId(TransformProperty.class));
            applyTransform(dest, transformProperty);

        }
        return dest;
    }

    public synchronized static Quaternionf getAbsoluteRotation(Component component, Quaternionf dest) {
        Component firstUndirty = loadDirtyAndFetchTop(component);
        if (stack.isEmpty()) {
            TransformProperty transformProperty = component.getProperty(Property.getTypeId(TransformProperty.class));
            return dest.set(transformProperty.getRotation());
        }
        if (firstUndirty == null) {
            dest.identity();
        } else if (!firstUndirty.hasProperty(Property.getTypeId(TransformProperty.class))) {
            dest.identity();
        } else {
            TransformProperty transformProperty = firstUndirty.getProperty(Property.getTypeId(TransformProperty.class));
            dest.set(transformProperty.getRotation());

        }

        Component dirty;
        while ((dirty = stack.poll()) != null) {
            TransformProperty transformProperty = dirty.getProperty(Property.getTypeId(TransformProperty.class));
            dest.mul(transformProperty.getRotation());
        }

        return dest;
    }


    /**
     * Loads dirty components on stack.
     *
     * @param component
     * @return * If there are some dirty parents of the component, returns the parent of the dirty parent closest to the root.
     * * If root is dirty, returns null.
     * * If there are no dirty parents, returns null.
     */
    protected static Component loadDirtyAndFetchTop(Component component) {
        stack.clear();
        int lastDirty = 0;
        int iteration = 0;
        for (Component comp = component; comp != null && comp.hasProperty(Property.getTypeId(TransformProperty.class)); comp = comp.getParent()) {
            iteration++;
            if (isDirty(comp)) lastDirty = iteration;
            stack.push(comp);
        }

        if (lastDirty == 0) {
            stack.clear();
            return null;
        } else {
            for (int i = lastDirty; i < iteration; i++)
                stack.pop();
            return stack.peek().getParent();
        }
    }

    private static boolean isDirty(Component comp) {
        return comp.<TransformProperty>getProperty(Property.getTypeId(TransformProperty.class)).isDirty();
    }

    private static void applyTransform(Matrix4f matrix, TransformProperty transform) {
        matrix.translate(transform.getTranslation());
        matrix.scale(transform.getScale());
        matrix.rotate(transform.getRotation());
    }

    private static Matrix4f tempM = new Matrix4f();

    public synchronized static Vector3f getAbsolutePosition(Component component, Vector3f dest) {
        tempM.identity();
        dest.zero();
        Matrix4f transform = getImmediateTransform(component, tempM);
        Vector3f vector3f = transform.transformPosition(dest);
        return vector3f;
    }


}

