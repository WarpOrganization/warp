package net.warpgame.engine.ai;

import net.warpgame.engine.ai.behaviortree.BehaviorTree;
import net.warpgame.engine.core.property.observable.ObservableProperty;

/**
 * @author Hubertus
 *         Created 10.01.2017
 */
public class AIProperty extends ObservableProperty {


    private BehaviorTree behaviorTree;

    public AIProperty(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public void setBehaviorTree(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public BehaviorTree getBehaviorTree() {
        return behaviorTree;
    }
}
