package pl.warp.engine.graphics.pipeline;

import java.util.function.BiConsumer;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 14
 */
public class LazyFlow<I, O> implements Flow<I, O> {

    private O output;
    private BiConsumer<I, O>  updater;

    public LazyFlow(O initialValue, BiConsumer<I, O> updater) {
        this.updater = updater;
        this.output = initialValue;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {

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
        updater.accept(input, output);
    }

}
