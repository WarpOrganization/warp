package net.warpgame.engine.ai;

import net.warpgame.engine.ai.behaviortree.BehaviorTree;
import net.warpgame.engine.core.property.Property;

/**
 * @author Hubertus
 * Created 10.01.2017
 */
public class AIProperty extends Property {

    private BehaviorTree behaviorTree;

    public AIProperty(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
        getOwner()
                .getContext()
                .getLoadedContext()
                .findOne(AIService.class)
                .get()
                .handlePropertyCreated(this);
    }

    public void setBehaviorTree(BehaviorTree behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    public BehaviorTree getBehaviorTree() {
        return behaviorTree;
    }
}
