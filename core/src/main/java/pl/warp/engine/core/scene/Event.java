package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 22
 */
public abstract class Event {
    abstract int getTypeID();

    private static int typeCounter = 0;

    public static int getNextEventTypeID() {
        return typeCounter++;
    }
}
