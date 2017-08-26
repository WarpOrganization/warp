package pl.warp.test.ai;

import pl.warp.engine.ai.behaviortree.LeafNode;
import pl.warp.engine.ai.behaviortree.Node;
import pl.warp.engine.ai.behaviortree.Ticker;
import pl.warp.engine.core.component.Component;
import pl.warp.test.DroneProperty;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 24.01.17
 */
public class FindTargetLeaf extends LeafNode {

    private final String TARGET_LIST = "targetList";

    private ArrayList<Component> targets;
    private DroneMemoryProperty memoryProperty;

    @Override
    public int tick(Ticker ticker, int delta) {
        if (targets.size() == 0) return Node.FAILURE;
        Component target = targets.get((int) Math.floor(Math.random() * targets.size()));
        memoryProperty.setTarget(target);
        if(target.hasProperty(DroneProperty.DRONE_PROPERTY_NAME)) memoryProperty.setTargetDroneProperty(target.getProperty(DroneProperty.DRONE_PROPERTY_NAME));
        return Node.SUCCESS;
    }

    @Override
    public void onOpen(Ticker ticker) {

    }

    @Override
    public void onReEnter(Ticker ticker) {

    }

    @Override
    protected void onInit(Ticker ticker) {
        Component owner = ticker.getOwner();
        DroneProperty droneProperty = owner.getProperty(DroneProperty.DRONE_PROPERTY_NAME);
        targets = droneProperty.getTargetList();
        memoryProperty = owner.getProperty(DroneMemoryProperty.DRONE_MEMORY_PROPERTY_NAME);
    }

    @Override
    protected void onClose(Ticker ticker) {

    }
}
