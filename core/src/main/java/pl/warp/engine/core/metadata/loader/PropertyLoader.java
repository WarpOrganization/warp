package pl.warp.engine.core.metadata.loader;

import org.apache.commons.lang3.tuple.Pair;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.metadata.Metadata;
import pl.warp.engine.core.metadata.PropertyMetadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 20
 */
public class PropertyLoader {

    private final ClassLoader classLoader = getClass().getClassLoader();

    public <T extends Component> Property<T> loadPropertyMetadata(PropertyMetadata metadata, T owner) {
        Class<? extends Property> propertyClass = getPropertyClass(metadata);
        Constructor<? extends Property> propertyConstructor = getPropertyConstructor(propertyClass, metadata);
        return constructProperty(propertyConstructor, metadata, owner);
    }

    private <T extends Component> Property<T> constructProperty(Constructor<? extends Property> propertyConstructor, PropertyMetadata metadata, T owner) {
        try {
            Stream<Object> metadataValues = getMetadataValues(metadata);
            Object[] parameterArguments = Stream.concat(Stream.of(owner), metadataValues).toArray();
            return propertyConstructor.newInstance(parameterArguments);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new MetadataLoadingException("Failed to construct property " + metadata.getPropertyClassName(), e);
        }
    }

    private Stream<Object> getMetadataValues(PropertyMetadata metadata) {
        return metadata.getPropertyValues().values().stream()
                .map(Metadata::getValue);
    }

    private Constructor<? extends Property> getPropertyConstructor(Class<? extends Property> propertyClass, PropertyMetadata metadata) {
        Class[] types = getPropertyDataTypes(metadata);
        Stream<Constructor> constructors = Stream.of(propertyClass.getConstructors());
        Stream<Pair<Constructor, Class[]>> constructorParamTypes = combineWithDataTypes(constructors);
        Stream<Constructor> constructor = findConstructor(types, constructorParamTypes);
        return constructor.findAny()
                .orElseThrow(() -> new MetadataLoadingException("Property not constructable from given data: "
                        + metadata.getPropertyClassName()));

    }

    private Stream<Pair<Constructor, Class[]>> combineWithDataTypes(Stream<Constructor> constructors) {
        return constructors.map(p -> Pair.of(p, getPropertyParameters(p)));
    }

    private Stream<Constructor> findConstructor(Class[] types, Stream<Pair<Constructor, Class[]>> constructorParamTypes) {
        return constructorParamTypes
                .filter(t -> Arrays.equals(t.getRight(), types))
                .map(Pair::getKey);
    }

    private Class[] getPropertyDataTypes(PropertyMetadata metadata) {
        return metadata.getPropertyValues().values().stream()
                .map(Metadata::getType)
                .toArray(Class[]::new);
    }

    private Class[] getPropertyParameters(Constructor p) {
        return Stream.of(p.getParameterTypes())
                .skip(1)
                .toArray(Class[]::new);
    }

    private Class<? extends Property> getPropertyClass(PropertyMetadata metadata) {
        try {
            return (Class<? extends Property>) classLoader.loadClass(metadata.getPropertyClassName());
        } catch (ClassNotFoundException e) {
            throw new MetadataLoadingException("Property class not found: " + metadata.getPropertyClassName(), e);
        }
    }
}
