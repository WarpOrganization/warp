package pl.warp.engine.ai.behaviourTree;

import pl.warp.engine.core.scene.Component;

import java.util.HashMap;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class BehaviourTree {
    private Node baseNode;
    private HashMap<String, Object> data = new HashMap<>();
    private Ticker ticker = new Ticker(this, data);
    private Component owner;

    public BehaviourTree(Node baseNode, Component owner) {
        this.baseNode = baseNode;
    }

    public void init(Component owner){
        this.owner = owner;
        data.put("owner", owner);
    }

    public void execute(int delta) {
        ticker.initializeTick(delta);
        baseNode.tick(ticker, delta);
    }

}
