package net.warpgame.engine.ai.property;

import net.warpgame.engine.ai.behaviortree.BehaviorTree;
import net.warpgame.engine.core.property.observable.ObservableProperty;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class AIProperty extends ObservableProperty {

    public static final String AI_POPERTY_NAME = "aiProperty";

    private BehaviorTree behaviorTree;

    public AIProperty(BehaviorTree behaviorTree) {
        super(AI_POPERTY_NAME);
        this.behaviorTree = behaviorTree;
    }

    public void setBehaviorTree(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public BehaviorTree getBehaviorTree() {
        return behaviorTree;
    }
}
