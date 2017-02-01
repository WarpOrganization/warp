package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.scene.Component;
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
        if (gunProperty == null)

            gunProperty = ((Component) ticker.getData(OWNER_KEY)).getProperty(GunProperty.GUN_PROPERTY_NAME);
        if (droneProperty == null)
            droneProperty = ((Component) ticker.getData(OWNER_KEY)).getProperty(DroneProperty.DRONE_PROPERTY_NAME);

        if (droneProperty.getHitPoints() > 0) {
            gunProperty.setTriggered(true);
            return Node.SUCCESS;
        } else {
            return Node.FAILURE;
        }
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
