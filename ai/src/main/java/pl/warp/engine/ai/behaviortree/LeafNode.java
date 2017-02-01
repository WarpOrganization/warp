package pl.warp.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class LeafNode extends Node {

    @Override
    public void addChild(Node child){

    }

    public abstract int tick(Ticker ticker, int delta);
}
