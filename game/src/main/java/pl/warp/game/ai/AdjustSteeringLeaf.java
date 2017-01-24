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


/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class AdjustSteeringLeaf extends LeafNode {

    private final String TARGET = "target";
    private final float SPEED = 40f;
    private final float ACCELERATION_FORCE = 10f;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);

    private TransformProperty ownerTransform;
    private TransformProperty targetTransform;
    private PhysicalBodyProperty bodyProperty;
    private Component owner;

    private Vector3f ownerDirection = new Vector3f();

    @Override
    public int tick(Ticker ticker) {
        setProperties(ticker);
        changeVelocity();
        rotate();
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

        desiredVelocity.set(targetTransform.getTranslation());
        desiredVelocity.sub(ownerTransform.getTranslation());
        desiredVelocity.normalize().mul(SPEED);

        desiredVelocity.sub(actualVelocity, velocityChange);

        if(velocityChange.length()<ACCELERATION_FORCE/bodyProperty.getMass()){
            bodyProperty.applyForce(velocityChange.mul(bodyProperty.getMass()));
        }else {
            bodyProperty.applyForce(velocityChange.normalize().mul(ACCELERATION_FORCE));
        }

    }

    private void updateDirections(){

    }

    private Vector3f targetDirection = new Vector3f();

    private void rotate() {
        Quaternionf goatFullRotation = Transforms.getAbsoluteRotation(owner);
        goatFullRotation.transform(ownerDirection.set(FORWARD_VECTOR));
        targetTransform.getTranslation().sub(ownerTransform.getTranslation(), targetDirection);
        targetDirection.normalize();
        //ownerDirection.
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
