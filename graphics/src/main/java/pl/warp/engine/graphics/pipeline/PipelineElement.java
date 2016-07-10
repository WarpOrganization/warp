package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 13
 */
public interface PipelineElement {
    void update(int delta);
    void init();
    void destroy();
    void onResize(int newWidth, int newHeight);
}
