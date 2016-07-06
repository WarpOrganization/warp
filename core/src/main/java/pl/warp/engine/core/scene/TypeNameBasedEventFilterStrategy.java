package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 14
 */
public class TypeNameBasedEventFilterStrategy implements EventFilterStrategy {
    private String typeName;

    public TypeNameBasedEventFilterStrategy(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean apply(Event event) {
        return event.getTypeName().equals(typeName);
    }
}
