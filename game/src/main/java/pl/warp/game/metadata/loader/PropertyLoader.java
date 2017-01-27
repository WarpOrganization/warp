package pl.warp.game.metadata.loader;

import pl.warp.game.metadata.ReflectionUtil;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.game.metadata.Metadata;
import pl.warp.game.metadata.PropertyMetadata;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jaca777
 *         Created 2016-12-10 at 20
 */
public class PropertyLoader {

    private final ClassLoader classLoader = getClass().getClassLoader();

    public <T extends Component> Property<T> loadPropertyMetadata(PropertyMetadata<T> metadata, T owner) {
        Class<T> propertyClass = getPropertyClass(metadata);
        Property<T> property = createBlankProperty(propertyClass);
        initProperty(property, propertyClass, owner);
        fillPropertyData(property, propertyClass, metadata);
        return property;
    }

    private <T extends Component> Class<T> getPropertyClass(PropertyMetadata<T> metadata) {
        try {
            return (Class<T>) classLoader.loadClass(metadata.getPropertyClassName());
        } catch (ClassNotFoundException e) {
            throw new MetadataLoadingException("Property class not found: " + metadata.getPropertyClassName(), e);
        }
    }

    private <T extends Component> void initProperty(Property<T> property, Class propertyClass, T owner) {
        try {
            Field ownerField = propertyClass.getField("owner");
            ownerField.setAccessible(true);
            ownerField.set(property, owner);
        } catch (Exception e) {
            throw new MetadataLoadingException("Failed to init property " + property.getName() + ".");
        }
    }

    private <T extends Component> void fillPropertyData(Property<T> property, Class propertyClass, PropertyMetadata<T> metadata) {
        Map<String, Field> fields = ReflectionUtil.getAllFieldsToSuperclass(propertyClass, Object.class).stream()
                .collect(Collectors.toMap(Field::getName, f -> f));
        for (Map.Entry<String, Metadata<T>> data : metadata.getPropertyValues().entrySet()) {
            try {
                Field field = fields.get(data.getKey());
                field.setAccessible(true);
                field.set(property, data.getValue());
            } catch (NullPointerException e) {
                throw new MetadataLoadingException("Field with name " + data.getKey()
                        + " not present in the " + propertyClass.getName() + " property.");
            } catch (IllegalAccessException e) {
                throw new MetadataLoadingException("Failed to set field named " + data.getKey()
                        + " in the " + propertyClass.getName() + " property.");
            }
        }
    }

    private <T extends Component> Property<T> createBlankProperty(Class<T> propertyClass) {
        try {
            ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();
            Constructor<Property<T>> constructor =
                    (Constructor<Property<T>>) reflection.newConstructorForSerialization(
                            propertyClass, Object.class.getDeclaredConstructor(new Class[0]));
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new MetadataLoadingException("Unable to create property " + propertyClass.getName() + ".", e);
        }
    }

}
