package pl.warp.engine.game.metadata.factory;

import pl.warp.engine.game.metadata.Metadata;
import pl.warp.engine.game.metadata.ReflectionUtil;
import pl.warp.engine.game.metadata.PropertyMetadata;
import pl.warp.engine.game.metadata.loader.MetadataLoadingException;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.core.component.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by user on 2017-01-19.
 */
public class PropertyMetadataFactory {
    public <T extends Component> PropertyMetadata<T> createPropertyMetadata(Property<T> property) {
        Class propertyClass = property.getClass();
        PropertyMetadata<T> metadata = new PropertyMetadata<>(propertyClass.getName());
        fillData(metadata, propertyClass, property);
        return metadata;
    }

    private <T extends Component> void fillData(PropertyMetadata<T> metadata, Class propertyClass, Property<T> property) {
        List<Field> fields = ReflectionUtil.getAllFieldsToSuperclass(propertyClass, Object.class);
        for (Field field : fields) {
            if (isDataField(field))
                setData(metadata, property, field);
        }
    }

    private boolean isDataField(Field field) {
        return !field.getName().equals("owner");
    }

    private <T extends Component> void setData(PropertyMetadata<T> metadata, Property<T> property, Field field) {
        try {
            field.setAccessible(true);
            metadata.getPropertyValues().put(field.getName(), createMetadata((T) field.get(property)));
        } catch (IllegalAccessException e) {
            throw new MetadataLoadingException("Unable to access data field " + field.getName()
                    + " in a class " + field.getDeclaringClass());
        }
    }

    private <T> Metadata<T> createMetadata(T t) {
        return new Metadata<>((Class<T>) t.getClass(), t);
    }


}
