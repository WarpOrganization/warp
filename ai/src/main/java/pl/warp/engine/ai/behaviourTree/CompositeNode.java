package pl.warp.engine.ai.behaviourTree;

import java.util.ArrayList;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class CompositeNode extends Node{
    protected ArrayList<Node> children = new ArrayList<>();
    public synchronized void addChildren(Node node){
        children.add(node);
    }
}
