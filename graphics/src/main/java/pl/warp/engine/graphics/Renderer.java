package pl.warp.engine.graphics;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 16
 */
public interface Renderer {
    void init();

    void initRendering();

    void render(Component component, MatrixStack stack);

    void destroy();

}
