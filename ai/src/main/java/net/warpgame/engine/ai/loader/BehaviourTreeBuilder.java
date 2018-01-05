package net.warpgame.engine.ai.loader;

import net.warpgame.engine.ai.behaviortree.BehaviorTree;
import net.warpgame.engine.core.component.Component;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class BehaviourTreeBuilder {

    private NodeBuilder baseNode;


    public BehaviorTree build(Component owner) {
        BehaviorTree tree = null;

        try {
            tree = new BehaviorTree(baseNode.build(), owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree;
    }

    public void setBaseNode(NodeBuilder baseNode) {
        this.baseNode = baseNode;
    }
}
