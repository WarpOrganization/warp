package pl.warp.engine.graphics.metadata;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.SimpleComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 12
 */
public class ComponentMetadata extends Component {

    private Set<PropertyMetadata> propertyMetadata = new HashSet<>();

    public ComponentMetadata(Component parent) {
        super(parent);
    }

    public ComponentMetadata(EngineContext context) {
        super(context);
    }

    public void addProperty(PropertyMetadata propertyMetadata) {
        this.propertyMetadata.add(propertyMetadata);
    }

    public Set<PropertyMetadata> getPropertyMetadata() {
        return propertyMetadata;
    }

    public Component newInstance(EngineContext context){
        return new SimpleComponent(context);
    }
}
