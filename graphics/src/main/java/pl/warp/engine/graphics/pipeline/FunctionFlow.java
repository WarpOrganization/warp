package pl.warp.engine.graphics.pipeline;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by user on 2017-03-16.
 */
public class FunctionFlow<I, O> implements Flow<I, O> {

    private I input;
    private O output;
    private BiConsumer<I, O> function;

    public FunctionFlow(BiConsumer<I, O> function, O output) {
        this.function = function;
        this.output = output;
    }

    @Override
    public void update() {
        this.function.accept(input, output);
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
        this.input = input;
    }
}
