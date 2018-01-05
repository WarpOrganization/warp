package net.warpgame.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 04.02.17
 */
public class BehaviorTreeNotFoundException extends RuntimeException {
    public BehaviorTreeNotFoundException(String message) {
        super(message);
    }

}
