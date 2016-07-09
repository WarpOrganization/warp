package pl.warp.engine.physics;

import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/7/16.
 */
public class MovementTask extends EngineTask {

    private Component parent;
    private Vector3f tmpSpeed;

    public MovementTask(Component parent) {
        tmpSpeed = new Vector3f();
        this.parent = parent;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(long delta) {
        float fdelta = (float) delta / 1000;
        parent.forEachChildren(component -> {
            if (isPhysicalBody(component) && isTransormable(component)) {
                PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                tmpSpeed.set(physicalBodyProperty.getVelocity());
                transformProperty.move(tmpSpeed.mul(fdelta));

                Vector3f torque = physicalBodyProperty.getTorque();
                transformProperty.rotate(torque.x * fdelta, torque.y * fdelta, torque.z * fdelta);

                if (isCollidable(component)) {
                    ColliderProperty colliderProperty = component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
                    colliderProperty.getCollider().setTransform(transformProperty.getTranslation(), transformProperty.getRotation());
                }
            }
        });
    }

    private boolean isPhysicalBody(Component component) {
        return component.hasEnabledProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
    }

    private boolean isTransormable(Component component) {
        return component.hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    private boolean isCollidable(Component component) {
        return component.hasEnabledProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);
    }
}
