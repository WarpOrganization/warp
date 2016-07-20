package pl.warp.engine.physics;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.joml.Quaternionf;
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
    private Vector3f tmpVelocity;
    private Vector3f tmpTorque;
    private Quaternionf tmpRotation;
    private static Logger logger = Logger.getLogger(MovementTask.class);


    public MovementTask(Component parent) {
        tmpVelocity = new Vector3f();
        tmpTorque = new Vector3f();
        tmpRotation = new Quaternionf();
        this.parent = parent;
    }

    @Override
    protected void onInit() {
        logger.info("Initializing movement task");
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        float fdelta = (float) delta / 1000;
        parent.forEachChildren(component -> {
            if (isPhysicalBody(component) && isTransormable(component)) {
                PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                tmpVelocity.set(physicalBodyProperty.getVelocity());
                tmpTorque.set(physicalBodyProperty.getTorque());
                if (isCollidable(component)) {
                    ColliderProperty colliderProperty = component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

                    physicalBodyProperty.setNextTickTranslation(tmpVelocity.mul(fdelta));
                    physicalBodyProperty.setNextTickRotation(tmpTorque.mul(fdelta));

                    tmpRotation.set(transformProperty.getRotation());
                    tmpRotation.rotateLocalX(tmpTorque.x);
                    tmpRotation.rotateLocalY(tmpTorque.y);
                    tmpRotation.rotateLocalZ(tmpTorque.z);
                    colliderProperty.getCollider().setTransform(tmpVelocity.add(transformProperty.getTranslation()), tmpRotation);
                } else {
                    transformProperty.move(tmpVelocity.mul(fdelta));

                    Vector3f torque = physicalBodyProperty.getTorque();
                    transformProperty.rotate(torque.x * fdelta, torque.y * fdelta, torque.z * fdelta);
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
