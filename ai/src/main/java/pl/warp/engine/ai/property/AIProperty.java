package pl.warp.engine.ai.property;

import pl.warp.engine.ai.behaviortree.BehaviourTree;
import pl.warp.engine.core.scene.Property;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class AIProperty extends Property {

    public static final String AI_POPERTY_NAME = "aiProperty";

    private BehaviourTree behaviourTree;

    public AIProperty(BehaviourTree behaviourTree) {
        super(AI_POPERTY_NAME);
        this.behaviourTree = behaviourTree;
    }

    @Override
    public void enable() {
        super.enable();
        behaviourTree.init(getOwner());
    }

    public void setBehaviourTree(BehaviourTree behaviourTree) {
        this.behaviourTree = behaviourTree;
    }

    public BehaviourTree getBehaviourTree() {
        return behaviourTree;
    }
}
