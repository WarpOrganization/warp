package net.warpgame.engine.postbuild.processing;

import java.util.List;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */
public class PipelineElement<T> {
    private Source<T> input;
    private List<Sink<T>> outputs;

    public PipelineElement(Source<T> input, List<Sink<T>> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    public void run() throws Exception {
        T elem = input.get();
        outputs.forEach(o -> o.process(elem));
    }
}
