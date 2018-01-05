package net.warpgame.engine.core.component;

/**
 * @author Hubertus
 * Created 09.12.2017
 */
public class IdExistsException extends RuntimeException {
    public IdExistsException() {
        super("Component with this ID already exists!");
    }
}
