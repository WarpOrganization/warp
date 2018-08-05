package net.warpgame.engine.postbuild.processing;

import static java.util.Objects.isNull;

/**
 * @author Jaca777
 * Created 2018-08-05 at 19
 */
public class CachingSourceWrapper<T> implements Source<T> {
    private Source<T> source;
    private T element;

    public CachingSourceWrapper(Source<T> source) {
        this.source = source;
    }

    @Override
    public T get() throws Exception {
        return isNull(element)
                ? (element = source.get())
                : element;
    }
}
