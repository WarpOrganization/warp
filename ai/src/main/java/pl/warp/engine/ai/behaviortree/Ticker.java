package pl.warp.engine.ai.behaviortree;

import pl.warp.engine.core.component.Component;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class Ticker<T extends Component> {

    private BehaviorTree<T> tree;
    private int currentTick = Integer.MIN_VALUE;
    private int delta;

    public Ticker(BehaviorTree<T> behaviorTree) {
        this.tree = behaviorTree;
    }

    public int tickNode(Node node) {
        node.enter(this);
        int status = node.tick(this, delta);
        if (status != Node.RUNNING) {
            node.close(this);
        }
        return status;
    }

    void initializeTick(int delta) {
        this.delta = delta;
        currentTick++;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public T getOwner() {
        return tree.getOwner();
    }
}
