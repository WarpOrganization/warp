package pl.warp.game.ai;

import pl.warp.engine.ai.behaviourTree.LeafNode;
import pl.warp.engine.ai.behaviourTree.Node;
import pl.warp.engine.ai.behaviourTree.Ticker;
import pl.warp.engine.core.scene.Component;
import pl.warp.game.DroneProperty;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class FindTargetLeaf extends LeafNode {

    private final String TARGET_LIST = "targetList";

    @Override
    public int tick(Ticker ticker) {
        Component owner = (Component) ticker.getData(OWNER_KEY);
        DroneProperty property = owner.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
        ArrayList<Component> targets = property.getTargetList();
        Component target = targets.get((int) Math.floor(Math.random() * targets.size()));
        ticker.setData(target, "target");
        ticker.setData(target.getProperty(DroneProperty.DRONE_PROPERTY_NAME), "droneProperty");
        return Node.SUCCESS;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onEnter(Ticker ticker) {

    }
}
