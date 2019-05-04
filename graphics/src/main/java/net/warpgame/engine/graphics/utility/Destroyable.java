package net.warpgame.engine.graphics.utility;

/**
 * @author MarconZet
 * Created 04.05.2019
 */
public interface Destroyable {
    void destroy();
    default boolean isDestroyed() {
        return false;
    }
}
