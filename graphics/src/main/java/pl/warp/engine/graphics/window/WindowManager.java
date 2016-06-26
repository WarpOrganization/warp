package pl.warp.engine.graphics.window;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 13
 */
public interface WindowManager {
    void makeWindow(Display display);
    void closeWindow();
    void resize(int w, int h);
}
