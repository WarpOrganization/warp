package pl.warp.test.tankAI;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.test.TankProperty;

/**
 * @author Hubertus
 *         Created 05.03.17
 */
public class HasTargetLeaf extends LeafNode {

    private TankProperty tankProperty;


    @Override
    public int tick(Ticker ticker, int delta) {
        if (tankProperty.getTargetTankProperty() != null && tankProperty.getTargetTankProperty().isAlive())
            return Node.SUCCESS;
        else return Node.FAILURE;
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
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
