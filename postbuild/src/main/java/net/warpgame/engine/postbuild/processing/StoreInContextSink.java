package net.warpgame.engine.postbuild.processing;

/**
 * @author Jaca777
 * Created 2018-08-11 at 00
 */
public class StoreInContextSink<T> implements Sink<T> {
    @Override
    public void process(T t, Context context) {
        context.put(t);
    }
}
