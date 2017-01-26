package pl.warp.test.ai;

import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.test.DroneProperty;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class HasTargetLeaf extends LeafNode {

    private final String TARGET_PROPERTY = "droneProperty";

    @Override
    public int tick(Ticker ticker, int delta) {
        DroneProperty d = (DroneProperty) ticker.getData(TARGET_PROPERTY);
        if (d != null && d.isEnabled() && d.getHitPoints() > 0) return Node.SUCCESS;
        else return Node.FAILURE;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
