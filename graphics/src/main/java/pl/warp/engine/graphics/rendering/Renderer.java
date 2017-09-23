package pl.warp.engine.graphics.rendering;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.graphics.utility.MatrixStack;

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
