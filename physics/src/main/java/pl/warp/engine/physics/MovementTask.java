package pl.warp.engine.physics;

import org.apache.log4j.Logger;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * @author Hubertus
 *         Created 7/7/16
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

    //TODO standing to false
    //TODO implement matrixstack
    Vector3f upVector = new Vector3f();
    @Override
    public void update(int delta) {
        float fdelta = (float) delta / 1000;
        parent.forEachChildren(component -> {
            if (isPhysicalBody(component) && isTransormable(component)) {
                PhysicalBodyProperty physicalBodyProperty = component.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
                TransformProperty transformProperty = component.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                GravityProperty gravityProperty = null;
                if (isGravityAffected(component)) {
                    gravityProperty = component.getProperty(GravityProperty.GRAVITY_PROPERTY_NAME);
                    if (!gravityProperty.isStanding())
                        physicalBodyProperty.getVelocity().add(gravityProperty.getDownVector());
                }
                tmpVelocity.set(physicalBodyProperty.getVelocity());
                tmpTorque.set(physicalBodyProperty.getAngularVelocity());

                if (isCollidable(component)) {
                    ColliderProperty colliderProperty = component.getProperty(ColliderProperty.COLLIDER_PROPERTY_NAME);

                    physicalBodyProperty.setNextTickTranslation(tmpVelocity.mul(fdelta));
                    physicalBodyProperty.setNextTickRotation(tmpTorque.mul(fdelta));
                    tmpRotation.set(transformProperty.getRotation());
                    tmpRotation.rotateLocalX(tmpTorque.x);
                    tmpRotation.rotateLocalY(tmpTorque.y);
                    tmpRotation.rotateLocalZ(tmpTorque.z);
                    physicalBodyProperty.recalculateInteriaTensor(tmpRotation);
                    colliderProperty.getCollider().setTransform(tmpVelocity.add(transformProperty.getTranslation()), tmpRotation);

                    if (gravityProperty != null) {
                        gravityProperty.getDownVector().negate(upVector);
                        if(physicalBodyProperty.getVelocity().dot(upVector)>0.001)
                            gravityProperty.setStanding(false);
                    }
                } else {
                    transformProperty.move(tmpVelocity.mul(fdelta));

                    Vector3f torque = physicalBodyProperty.getAngularVelocity();
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

    private boolean isGravityAffected(Component component) {
        return component.hasEnabledProperty(GravityProperty.GRAVITY_PROPERTY_NAME);
    }
}
