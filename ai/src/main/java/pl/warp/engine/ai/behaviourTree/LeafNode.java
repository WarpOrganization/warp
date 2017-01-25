package pl.warp.engine.ai.behaviourTree;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class LeafNode extends Node {

    @Override
    public void addChild(Node child){

    }

    public abstract int tick(Ticker ticker);
}
