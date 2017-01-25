package pl.warp.game.ai;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import static java.lang.Math.PI;


/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class AdjustSteeringLeaf extends LeafNode {

    private final String TARGET = "target";
    private final float GOAL_SPEED = 40f;
    private final float ACCELERATION_FORCE = 10f;
    private final float ANGULAR_ACCELERATION = 0.2f;
    private final float GOAL_ROTATION_SPEED = 0.9f;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    private TransformProperty ownerTransform;
    private TransformProperty targetTransform;
    private PhysicalBodyProperty bodyProperty;
    private Component owner;

    private Vector3f ownerDirection = new Vector3f();

    @Override
    public int tick(Ticker ticker, int delta) {
        setProperties(ticker);
        updateDirections();
        changeVelocity();
        rotate(delta);
        return Node.SUCCESS;
    }

    private void setProperties(Ticker ticker) {
        targetTransform = ((Component) ticker.getData(TARGET)).getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        owner = (Component) ticker.getData(OWNER_KEY);
        ownerTransform = owner.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        bodyProperty = owner.getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

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

    private void rotate(int delta) {

        targetTransform.getTranslation().sub(ownerTransform.getTranslation(), targetDirection);
        targetDirection.normalize();
        rotationChange.x = (float) ((Math.atan2(ownerDirection.y, ownerDirection.z) * 180 / PI) - (Math.atan2(targetDirection.y, targetDirection.z) * 180 / PI));
        rotationChange.y = (float) ((Math.atan2(ownerDirection.x, ownerDirection.z) * 180 / PI) - (Math.atan2(targetDirection.x, targetDirection.z) * 180 / PI));

        rotationChange.normalize();
        rotationChange.mul(GOAL_ROTATION_SPEED, goalAngularVelocity);
        goalAngularVelocity.sub(bodyProperty.getAngularVelocity(), angularVelocityChange);
        if (angularVelocityChange.length() > ACCELERATION_FORCE * delta / bodyProperty.getUniversalRotationInertia()) {
            rotationChange.normalize();
            rotationChange.mul(ACCELERATION_FORCE*delta);
            bodyProperty.addAngularVelocity(rotationChange.div(bodyProperty.getUniversalRotationInertia()));
        } else {
            bodyProperty.addAngularVelocity(angularVelocityChange);
        }
        //bodyProperty.setAngularVelocity(rotationChange);
        //bodyProperty.addAngularVelocity(rotationChange.div(bodyProperty.getUniversalRotationInertia()));

    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
