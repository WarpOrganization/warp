package pl.warp.engine.ai.behaviortree;

import pl.warp.engine.core.component.Component;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class BehaviorTree<T extends Component> {
    private Node baseNode;
    private Ticker<T> ticker = new Ticker<>(this);
    private T owner;
    private boolean initialized;

    public BehaviorTree(Node baseNode, T owner) {
        this.baseNode = baseNode;
        this.owner = owner;
    }

    public void init() {
        initialized = true;
        baseNode.init(ticker);
    }

    public void execute(int delta) {
        ticker.initializeTick(delta);
        baseNode.tick(ticker, delta);
    }

    public T getOwner() {
        return owner;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
