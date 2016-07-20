package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 17
 */
public class Pipeline {
    private final PipelineElement[] elements;

    public Pipeline(PipelineElement[] elements) {
        this.elements = elements;
    }

    public void init() {
        for (PipelineElement element : elements)
            element.init();
        connectElements();
    }

    private void connectElements() {
        PipelineElement lastElement = elements[0];
        for (int i = 1; i < elements.length; i++) {
            PipelineElement element = elements[i];
            ((Sink) element).setInput(((Source) lastElement).getOutput());
            lastElement = element;
        }
    }

    public void update(int delta) {
        for (PipelineElement element : elements)
            element.update(delta);
    }

    public void resize(int newWidth, int newHeight) {
        for (PipelineElement element : elements)
            element.onResize(newWidth, newHeight);
    }

    public void destroy() {
        for (PipelineElement element : elements)
            element.destroy();
    }
}
