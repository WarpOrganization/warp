package pl.warp.test.tankAI;

import org.joml.Vector3f;
import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.test.TankProperty;

import static java.lang.Math.PI;

/**
 * @author Hubertus
 *         Created 05.03.17
 */
public class AdjustTurretLeaf extends LeafNode {

    private PhysicalBodyProperty bodyProperty;
    private TransformProperty ownerTransform;
    private TankProperty tankProperty;
    private TransformProperty targetTransform;

    private final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, 1);

    private Vector3f turretDirection = new Vector3f();

    private final float ACCURACY = 0.01f;//in radians

    @Override
    public int tick(Ticker ticker, int delta) {
        targetTransform = tankProperty.getTarget().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        Transforms.getAbsoluteRotation(tankProperty.getTankTurret()).transform(turretDirection.set(FORWARD_VECTOR));
        rotate(delta);
        return Node.SUCCESS;
    }

    @Override
    protected void onOpen(Ticker ticker) {

    }

    @Override
    protected void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {
        ownerTransform = ticker.getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        tankProperty = ticker.getOwner().getProperty(TankProperty.TANK_PROPERTY_NAME);
        bodyProperty = tankProperty.getTankTurret().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }

    private Vector3f targetDirection = new Vector3f();
    private Vector3f rotationChange = new Vector3f();


    private boolean rotate(int delta) {
        if (targetTransform != null) {
            targetTransform.getTranslation().sub(ownerTransform.getTranslation(), targetDirection);
            targetDirection.normalize();
            rotationChange.set(0);
            //rotationChange.x = (float) ((Math.atan2(turretDirection.y, turretDirection.z) * 180 / PI) - (Math.atan2(targetDirection.y, targetDirection.z) * 180 / PI));
            rotationChange.y = (float) ((Math.atan2(turretDirection.z, turretDirection.x) * 180 / PI) - (Math.atan2(targetDirection.z, targetDirection.x) * 180 / PI));
            //rotationChange.z = (float) ((Math.atan2(turretDirection.y, turretDirection.x) * 180 / PI) - (Math.atan2(targetDirection.y, targetDirection.x) * 180 / PI));
            boolean isAligned = false;
            if (rotationChange.y < ACCURACY) isAligned = true;

            if (rotationChange.length() > 0) rotationChange.normalize();
            bodyProperty.setAngularVelocity(rotationChange);
            return isAligned;
        } else return false;
    }
}
