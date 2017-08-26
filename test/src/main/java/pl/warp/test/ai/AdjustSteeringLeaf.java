package pl.warp.test.ai;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.common.properties.TransformProperty;
import pl.warp.engine.common.properties.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import static java.lang.Math.PI;


/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class AdjustSteeringLeaf extends LeafNode {

    private final String TARGET = "target";
    private final float GOAL_SPEED = 120f;
    private final float ACCELERATION_FORCE = 10f;
    private final float ANGULAR_ACCELERATION = 0.2f;
    private final float GOAL_ROTATION_SPEED = 0.9f;
    private final float ACCURACY = 0.15f;//in radians

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    private TransformProperty ownerTransform;
    private TransformProperty targetTransform;
    private PhysicalBodyProperty bodyProperty;
    private DroneMemoryProperty memoryProperty;
    private Component owner;
    private Vector3f ownerDirection = new Vector3f();


    @Override
    protected void onInit(Ticker ticker) {
        owner = ticker.getOwner();
        ownerTransform = owner.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        memoryProperty = owner.getProperty(DroneMemoryProperty.DRONE_MEMORY_PROPERTY_NAME);
        bodyProperty = owner.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }

    @Override
    public int tick(Ticker ticker, int delta) {
        setProperties(ticker);
        updateDirections();
        changeVelocity();
        return rotate(delta) ? Node.SUCCESS : Node.FAILURE;
    }

    private void setProperties(Ticker ticker) {
        if(memoryProperty.getTarget().hasEnabledProperty(TransformProperty.TRANSFORM_PROPERTY_NAME))
            targetTransform = memoryProperty.getTarget().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    private Vector3f actualVelocity = new Vector3f();
    private Vector3f desiredVelocity = new Vector3f();
    private Vector3f velocityChange = new Vector3f();

    private void changeVelocity() {
        actualVelocity.set(bodyProperty.getVelocity());
        ownerDirection.mul(GOAL_SPEED, desiredVelocity);

        desiredVelocity.sub(actualVelocity, velocityChange);

        if (velocityChange.length() < ACCELERATION_FORCE / bodyProperty.getMass()) {
            bodyProperty.applyForce(velocityChange.mul(bodyProperty.getMass()));
        } else {
            bodyProperty.applyForce(velocityChange.normalize().mul(ACCELERATION_FORCE));
        }

    }

    private void updateDirections() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(owner);
        goatFullRotation.transform(ownerDirection.set(FORWARD_VECTOR));
        ownerDirection.normalize();
    }

    private Vector3f targetDirection = new Vector3f();
    private Vector3f rotationChange = new Vector3f();
    private Vector3f goalAngularVelocity = new Vector3f();
    private Vector3f angularVelocityChange = new Vector3f();

    private boolean rotate(int delta) {
        if(targetTransform != null) {
            targetTransform.getTranslation().sub(ownerTransform.getTranslation(), targetDirection);
            targetDirection.normalize();
            rotationChange.x = (float) ((Math.atan2(ownerDirection.y, ownerDirection.z) * 180 / PI) - (Math.atan2(targetDirection.y, targetDirection.z) * 180 / PI));
            rotationChange.y = (float) ((Math.atan2(ownerDirection.x, ownerDirection.z) * 180 / PI) - (Math.atan2(targetDirection.x, targetDirection.z) * 180 / PI));

            boolean isAligned = false;
            if (rotationChange.x < ACCURACY && rotationChange.y < ACCURACY) isAligned = true;

            rotationChange.normalize();
            rotationChange.mul(GOAL_ROTATION_SPEED, goalAngularVelocity);
            goalAngularVelocity.sub(bodyProperty.getAngularVelocity(), angularVelocityChange);
            //TODO rethink these calculations
            bodyProperty.addAngularVelocity(angularVelocityChange);
            return isAligned;
        } else return false;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onReEnter(Ticker ticker) {

    }


}
