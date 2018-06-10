package net.warpgame.engine.ai.loader;

import net.warpgame.engine.ai.behaviortree.BehaviorTree;
import net.warpgame.engine.core.component.Component;

/**
 * @author Hubertus
 * Created 19.01.2017
 */
public class BehaviourTreeBuilder {

    private NodeBuilder baseNode;


    public BehaviorTree build(Component owner) throws BehaviourTreeBuildException {
        BehaviorTree tree = null;
        tree = new BehaviorTree(baseNode.build(), owner);
        return tree;
    }

    public void setBaseNode(NodeBuilder baseNode) {
        this.baseNode = baseNode;
    }
}
