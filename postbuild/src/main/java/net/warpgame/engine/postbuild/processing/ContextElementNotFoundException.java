package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-11 at 23
 */
public class ContextElementNotFoundException extends RuntimeException {

    public ContextElementNotFoundException(String elementClassName) {
        super("Context element of type " + elementClassName + " not found");
    }
}
