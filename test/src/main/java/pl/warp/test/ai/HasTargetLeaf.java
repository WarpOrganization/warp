package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.test.DroneProperty;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class HasTargetLeaf extends LeafNode {

    private DroneMemoryProperty memoryProperty;

    @Override
    public int tick(Ticker ticker, int delta) {
        DroneProperty droneProperty = memoryProperty.getTargetDroneProperty();
        if (droneProperty != null && droneProperty.isEnabled() && droneProperty.getHitPoints() > 0) return Node.SUCCESS;
        else return Node.FAILURE;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {
        memoryProperty = ticker.getOwner().getProperty(DroneMemoryProperty.DRONE_MEMORY_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
