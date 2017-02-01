package pl.warp.engine.ai.behaviourtree;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class CompositeNode extends Node{
    protected ArrayList<Node> children = new ArrayList<>();

    @Override
    public synchronized void addChild(Node node){
        children.add(node);
    }
}
