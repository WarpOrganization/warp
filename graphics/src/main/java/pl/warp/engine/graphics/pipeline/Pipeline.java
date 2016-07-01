package pl.warp.engine.graphics.pipeline;

import java.util.List;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 17
 */
public class Pipeline {
    private final PipelineElement[] elements;

    public Pipeline(PipelineElement[]elements) {
        this.elements = elements;
    }

    public void init() {
        for(PipelineElement element : elements)
            element.init();
    }

    public void render() {
        for(PipelineElement element : elements)
            element.render();
    }

    public void resize(int newWidth, int newHeight) {
        for(PipelineElement element : elements)
            element.onResize(newWidth, newHeight);
    }
}
