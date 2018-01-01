package pl.warp.engine.ai;

import pl.warp.engine.ai.behaviortree.BehaviorTree;
import pl.warp.engine.core.property.observable.ObservableProperty;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class AIProperty extends ObservableProperty {

    public static final String NAME = "aiProperty";

    private BehaviorTree behaviorTree;

    public AIProperty(BehaviorTree behaviorTree) {
        super(NAME);
        this.behaviorTree = behaviorTree;
    }

    public void setBehaviorTree(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public BehaviorTree getBehaviorTree() {
        return behaviorTree;
    }
}
