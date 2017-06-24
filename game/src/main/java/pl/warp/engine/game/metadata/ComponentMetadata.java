package pl.warp.engine.game.metadata;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.game.scene.GameSceneComponent;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 12
 */
public class ComponentMetadata implements Serializable {

    private Set<PropertyMetadata> propertyMetadata = new HashSet<>();

    public void addProperty(PropertyMetadata propertyMetadata) {
        this.propertyMetadata.add(propertyMetadata);
    }

    public Set<PropertyMetadata> getPropertyMetadata() {
        return propertyMetadata;
    }

    public Component newInstance(GameContext context){
        return new GameSceneComponent(context);
    }
}
