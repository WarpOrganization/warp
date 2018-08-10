package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-05 at 18
 */

@FunctionalInterface
public interface Processor<T, R> {
    R process(T t, Context context);

    static <T> Processor<T, T> around(Processor<T, ?> around) {
        return ((t, c) -> {
            around.process(t, c);
            return t;
        });
    }


    static <T> Processor<T, T> around(Sink<T> around) {
        return ((t, c) -> {
            around.process(t, c);
            return t;
        });
    }
}
