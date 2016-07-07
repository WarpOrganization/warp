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

    public MovementTask(Component parent) {

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
        for (Component component : parent.getChildren()) {
            PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
            TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            ColliderProperty colliderProperty = component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);


            physicalBodyProperty.addSpeed(physicalBodyProperty.getAcceleration());
            transformProperty.move(physicalBodyProperty.getSpeed());

            Vector3f torque = physicalBodyProperty.getTorque();
            transformProperty.rotate(torque.x, torque.y, torque.z);

            colliderProperty.getLogic().addTransform(physicalBodyProperty.getSpeed(), torque);
        }
    }
}
