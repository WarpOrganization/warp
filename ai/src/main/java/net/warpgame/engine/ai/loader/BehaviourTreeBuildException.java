package net.warpgame.engine.ai.loader;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class BehaviourTreeBuildException extends RuntimeException{
    public BehaviourTreeBuildException(Exception cause) {
        super("Behaviour tree builder encountered a problem.");
        initCause(cause);
    }
}
