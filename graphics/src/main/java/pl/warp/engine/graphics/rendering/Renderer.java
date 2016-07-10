package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.scene.Scene;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 16
 */
public interface Renderer {
    void init();
    void render(Scene scene, int delta);
    void destroy();
}
