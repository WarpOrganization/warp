package net.warpgame.engine.postbuild.processing;

import java.util.function.Function;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public class PrintSink <T> implements Sink<T> {

    private Function<T, String> stringExtractor;

    public PrintSink(Function<T, String> stringExtractor) {
        this.stringExtractor = stringExtractor;
    }

    @Override
    public void process(T t) {
        String string = stringExtractor.apply(t);
        System.out.println(string);
    }
}
