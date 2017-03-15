package pl.warp.engine.graphics.pipeline;

/**
 * @author Jaca777
 *         Created 2017-03-15 at 19
 */
public class MultiSink<I> implements Sink<I> {

    private Sink<I>[] sinks;

    public MultiSink(Sink<I>[] sinks) {
        this.sinks = sinks;
    }

    @Override
    public void update() {
        for(Sink sink : sinks) sink.update();
    }

    @Override
    public void init() {
        for(Sink sink : sinks) sink.init();
    }

    @Override
    public void destroy() {
        for(Sink sink : sinks) sink.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        for(Sink sink : sinks) sink.onResize(newWidth, newHeight);
    }

    @Override
    public void setInput(I input) {
        for(Sink sink : sinks) sink.setInput(input);
    }
}
