package pl.warp.engine.game.metadata;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 12
 */
public class Metadata <T> {
    private Class<T> type;
    private T value;

    public Metadata(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

