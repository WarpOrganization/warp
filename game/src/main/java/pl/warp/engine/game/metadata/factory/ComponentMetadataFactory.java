package pl.warp.engine.game.metadata.factory;

import pl.warp.engine.game.metadata.ComponentMetadata;
import pl.warp.engine.game.metadata.PropertyMetadata;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by user on 2017-01-19.
 */
public class ComponentMetadataFactory {
    private PropertyMetadataFactory metadataFactory;

    public ComponentMetadataFactory(PropertyMetadataFactory metadataFactory) {
        this.metadataFactory = metadataFactory;
    }

    public ComponentMetadata createComponentMetadata(Component component){
        ComponentMetadata metadata = new ComponentMetadata();
        Set<Property> properties = component.getProperties();
        Set<PropertyMetadata> propertyMetadata = (Set<PropertyMetadata>) properties.stream()
                .map(metadataFactory::createPropertyMetadata)
                .collect(Collectors.toSet());
       return metadata;
    }
}
