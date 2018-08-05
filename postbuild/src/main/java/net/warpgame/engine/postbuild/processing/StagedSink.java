package net.warpgame.engine.postbuild.processing;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jaca777
 * Created 2018-08-05 at 19
 */
public class StagedSink<T> implements Sink<T> {
    private List<Sink<T>> sinks;

    public StagedSink(Sink<T>... sinks) {
        this.sinks = Arrays.asList(sinks);
    }

    public void addStage(Sink<T> sink) {
        this.sinks.add(sink);
    }

    @Override
    public void process(T t) {
        sinks.forEach(s -> s.process(t));
    }
}
