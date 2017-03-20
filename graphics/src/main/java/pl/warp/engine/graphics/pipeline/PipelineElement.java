package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.Graphics;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 13
 */
public interface PipelineElement {
    void update();
    void init(Graphics graphics);
    void destroy();
    void onResize(int newWidth, int newHeight);
}
