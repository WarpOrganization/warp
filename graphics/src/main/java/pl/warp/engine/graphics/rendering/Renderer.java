package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.graphics.math.MatrixStack;

/**
 * @author Jaca777
 *         Created 2016-07-10 at 16
 */
public interface Renderer {
    void init();

    void initRendering(int delta);

    void render(Component component, MatrixStack stack);

    void destroy();
}
