package net.warpgame.engine.ai.loader;

/**
 * @author Hubertus
 * Created 09.06.2018
 */
public class BehaviourTreeLoadException extends RuntimeException {
    public BehaviourTreeLoadException(Exception cause) {
        super("Behaviour tree loader encountered a problem.");
        initCause(cause);
    }
}
