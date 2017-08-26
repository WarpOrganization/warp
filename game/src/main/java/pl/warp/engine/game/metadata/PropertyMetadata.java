package pl.warp.engine.game.metadata;

import pl.warp.engine.core.component.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 12
 */
public class PropertyMetadata<T extends Component> implements Serializable {
    private String propertyClassName;
    private Map<String, Metadata<T>> propertyValues;

    public PropertyMetadata(String propertyClassName) {
        this.propertyClassName = propertyClassName;
        this.propertyValues = new HashMap<>();
    }

    public String getPropertyClassName() {
        return propertyClassName;
    }

    public Map<String, Metadata<T>> getPropertyValues() {
        return propertyValues;
    }
}
