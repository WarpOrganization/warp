package pl.warp.engine.ai.loader;

import pl.warp.engine.ai.behaviourtree.BehaviourTree;
import pl.warp.engine.core.scene.Component;

/**
 * @author Hubertus
 *         Created 19.01.2017
 */
public class BehaviourTreeBuilder {

    private NodeBuilder baseNode;


    public BehaviourTree build(Component owner) {
        BehaviourTree tree = null;

        try {
            tree = new BehaviourTree(baseNode.build(), owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree;
    }

    public void setBaseNode(NodeBuilder baseNode) {
        this.baseNode = baseNode;
    }
}
