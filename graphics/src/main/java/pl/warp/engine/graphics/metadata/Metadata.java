package pl.warp.engine.graphics.metadata;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 12
 */
public interface Metadata <T> {
    Class<T> getType();
    T getValue();
    void setValue(T t);
}
