package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.test.DroneProperty;
import pl.warp.test.GunProperty;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class ShootLeaf extends LeafNode {

    GunProperty gunProperty;
    DroneProperty droneProperty;

    @Override
    public int tick(Ticker ticker, int delta) {
        if(gunProperty.isEnabled()) {
            gunProperty.setTriggered(true);
            return Node.SUCCESS;
        } else return Node.FAILURE;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {
        droneProperty = ticker.getOwner().getProperty(DroneProperty.DRONE_PROPERTY_NAME);
        gunProperty = ticker.getOwner().getProperty(GunProperty.GUN_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
