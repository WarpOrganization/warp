package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.test.DroneProperty;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class FindTargetLeaf extends LeafNode {

    private final String TARGET_LIST = "targetList";

    @Override
    public int tick(Ticker ticker, int delta) {
        Component owner = (Component) ticker.getData(OWNER_KEY);
        DroneProperty property = owner.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
        ArrayList<Component> targets = property.getTargetList();
        if (targets.size() == 0) return Node.FAILURE;
        Component target = targets.get((int) Math.floor(Math.random() * targets.size()));
        ticker.setData(target, "target");
        if(target.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) ticker.setData(target.getProperty(DroneProperty.DRONE_PROPERTY_NAME), "droneProperty");
        return Node.SUCCESS;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
