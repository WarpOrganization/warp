package pl.warp.test.tankAI;

import org.joml.Vector3f;
import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.test.TankProperty;

/**
 * @author Hubertus
 *         Created 05.03.17
 */
public class ChooseTargetLeaf extends LeafNode {

    private TankProperty tankProperty;
    private TransformProperty transformProperty;

    private Vector3f distanceVector = new Vector3f();

    @Override
    public int tick(Ticker ticker, int delta) {
        Component closestComponent = null;
        TankProperty closestTankProperty = null;
        float distance = Float.MAX_VALUE;
        for (int i = 0; i < tankProperty.getTargets().size(); i++) {
            Component target = tankProperty.getTargets().get(i);
            TankProperty targetProperty = target.getProperty(TankProperty.TANK_PROPERTY_NAME);
            if (targetProperty.isAlive()) {
                TransformProperty targetTransform = target.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
                transformProperty.getTranslation().sub(targetTransform.getTranslation(), distanceVector);
                if(distanceVector.length()<distance){
                    closestComponent = target;
                    closestTankProperty = targetProperty;
                    distance = distanceVector.length();
                }
                tankProperty.setTarget(target);
                tankProperty.setTargetTankProperty(targetProperty);
            } else
                tankProperty.getTargets().remove(i);
        }
        if(closestComponent!=null){
            tankProperty.setTargetTankProperty(closestTankProperty);
            tankProperty.setTarget(closestComponent);
            return Node.SUCCESS;
        }
        return Node.FAILURE;
    }

    @Override
    protected void onOpen(Ticker ticker) {

    }

    @Override
    protected void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {
        tankProperty = ticker.getOwner().getProperty(TankProperty.TANK_PROPERTY_NAME);
        transformProperty = ticker.getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
