package pl.warp.game.script.updatescheduler;

/**
 * @author Jaca777
 *         Created 2017-02-07 at 01
 */
public interface UpdateScheduler {
    void update(int delta);
    boolean pollUpdate();
}
