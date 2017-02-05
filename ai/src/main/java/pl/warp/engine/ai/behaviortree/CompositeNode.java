package pl.warp.engine.ai.behaviortree;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class CompositeNode extends Node {
    protected ArrayList<Node> children = new ArrayList<>();

    @Override
    public synchronized void addChild(Node node) {
        children.add(node);
    }

    @Override
    public void init(Ticker ticker) {
        onInit(ticker);
        children.forEach((c) -> c.init(ticker));
    }
}
