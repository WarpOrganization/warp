package pl.warp.engine.graphics.pipeline;

import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2017-02-25 at 14
 */
public class MultiFlow<I, O> implements Flow<I,O[]> {
    private Flow<I, O>[] flows;
    private IntFunction<O[]> outputFactory;
    private O[] output;

    public MultiFlow(Flow<I, O>[] flows, IntFunction<O[]> outputFactory) {
        this.flows = flows;
        this.outputFactory = outputFactory;
    }

    @Override
    public void update() {
        for(Flow flow : flows)
            flow.update();
    }

    @Override
    public void init() {
        for(Flow flow : flows)
            flow.init();
        loadOutputs();
    }

    private void loadOutputs() {
        this.output = Stream.of(flows).map(Flow::getOutput).toArray(outputFactory);
    }

    @Override
    public void destroy() {
        for(Flow flow : flows)
            flow.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        for(Flow flow : flows)
            flow.onResize(newWidth, newHeight);
    }

    @Override
    public O[] getOutput() {
        return output;
    }

    @Override
    public void setInput(I input) {
        for(Flow flow : flows)
            flow.setInput(input);
    }
}
