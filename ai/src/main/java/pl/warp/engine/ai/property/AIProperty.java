package pl.warp.engine.ai.property;

import pl.warp.engine.ai.behaviourTree.BehaviourTree;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class AIProperty extends Property {

    public static final String AI_POPERTY_NAME = "aiProperty";

    private BehaviourTree behaviourTree;

    public AIProperty(Component owner, BehaviourTree behaviourTree) {
        super(owner, AI_POPERTY_NAME);
        behaviourTree.init(owner);
        this.behaviourTree = behaviourTree;
    }

    public void setBehaviourTree(BehaviourTree behaviourTree) {
        this.behaviourTree = behaviourTree;
    }

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }
}
