package pl.warp.engine.graphics.metadata.loader;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.graphics.metadata.Metadata;
import pl.warp.engine.graphics.metadata.PropertyMetadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 20
 */
public class PropertyLoader {

    private final ClassLoader classLoader = getClass().getClassLoader();

    public <T extends Component>  Property<T> loadMetadata(PropertyMetadata metadata) {
        Class<? extends Property> propertyClass = getPropertyClass(metadata);
        Constructor<? extends Property> propertyConstructor = getPropertyConsctructor(propertyClass, metadata);
        Property<T> property = constructProperty(propertyConstructor, metadata);
        return property;
    }

    private <T extends Component> Property<T> constructProperty(Constructor<? extends Property> propertyConstructor, PropertyMetadata metadata) {
        try {
            Object[] arguments = metadata.getPropertyValues().values().stream()
                    .map(Metadata::getValue)
                    .toArray();
            return propertyConstructor.newInstance(arguments);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new MetadataLoadingException("Failed to construct property " + metadata.getPropertyClassName(), e);
        }
    }

    private Constructor<? extends Property> getPropertyConsctructor(Class<? extends Property> propertyClass, PropertyMetadata metadata) {
        try {
            Class<?>[] types = metadata.getPropertyValues().values().stream()
                    .map(Metadata::getType)
                    .toArray(Class<?>[]::new);
            return propertyClass.getConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new MetadataLoadingException("Property " + metadata.getPropertyClassName() + " is not constructable from given data.", e);
        }
    }

    private Class<? extends Property> getPropertyClass(PropertyMetadata metadata)  {
        try {
            return (Class<? extends Property>) classLoader.loadClass(metadata.getPropertyClassName());
        } catch (ClassNotFoundException e) {
            throw new MetadataLoadingException("Property class not found: " + metadata.getPropertyClassName(), e);
        }
    }
}
