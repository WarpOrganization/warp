package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.Graphics;

import java.util.function.BiConsumer;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 14
 */
public class LazyFlow<I, O> implements Flow<I, O> {

    private I input;
    private O output;
    private BiConsumer<I, O> lazyUpdater;

    public LazyFlow(O initialValue, BiConsumer<I, O> lazyUpdater) {
        this.lazyUpdater = lazyUpdater;
        this.output = initialValue;
    }

    @Override
    public void update() {

    }

    @Override
    public void init(Graphics g) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onResize(int newWidth, int newHeight) {

    }

    @Override
    public O getOutput() {
        return output;
    }

    @Override
    public void setInput(I input) {
        this.input = input;
        lazyUpdater.accept(input, output);
    }

}
