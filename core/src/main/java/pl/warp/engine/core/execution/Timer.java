package pl.warp.engine.core.execution;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 12
 */
public interface Timer {
    void await();
    int getDelta();
    void setUps(int ups);
}
