package net.warpgame.engine.graphics.rendering;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.graphics.utility.MatrixStack;

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
