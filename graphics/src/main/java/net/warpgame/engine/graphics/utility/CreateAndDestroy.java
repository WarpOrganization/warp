package net.warpgame.engine.graphics.utility;

/**
 * @author MarconZet
 * Created 05.04.2019
 */
public interface CreateAndDestroy extends Destroyable {
    void create();

    default boolean isCreated() {
        throw new UnsupportedOperationException();
    }
}
