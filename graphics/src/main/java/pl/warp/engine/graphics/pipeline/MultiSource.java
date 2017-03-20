package pl.warp.engine.graphics.pipeline;

import pl.warp.engine.graphics.Graphics;

import java.util.function.IntFunction;

/**
 * @author Jaca777
 *         Created 2017-03-15 at 19
 */
public class MultiSource<O> implements Source<O[]>{

    private Source<O>[] sources;
    private O[] output;

    public MultiSource(Source<O>[] sources, IntFunction<O[]> outputFactory) {
        this.sources = sources;
        initOutput(outputFactory);
    }

    private void initOutput(IntFunction<O[]> outputFactory) {
        this.output = outputFactory.apply(sources.length);
        for(int i = 0; i < sources.length; i++){
            this.output[i] = sources[i].getOutput();
        }
    }

    @Override
    public void update() {
        for(Source source : sources) source.update();
    }

    @Override
    public void init(Graphics g) {
        for(Source source : sources) source.init(g);
    }

    @Override
    public void destroy() {
        for(Source source : sources) source.destroy();
    }

    @Override
    public void onResize(int newWidth, int newHeight) {
        for(Source source : sources) source.onResize(newWidth, newHeight);
    }

    @Override
    public O[] getOutput() {
        return output;
    }
}
