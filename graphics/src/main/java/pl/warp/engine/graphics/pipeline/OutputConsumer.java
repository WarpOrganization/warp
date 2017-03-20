package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.Graphics;

import java.util.function.Consumer;

/**
 * @author Jaca777
 *         Created 2017-03-15 at 19
 */
public class OutputConsumer<T> implements Sink<T> {

    private T output;
    private Consumer<T> consumer;

    public OutputConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void update() {
        consumer.accept(output);
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
    public void setInput(T input) {
       this.output = input;
    }

}
