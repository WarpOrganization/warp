package pl.warp.engine.core.script.initialization;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.core.script.annotation.OwnerProperty;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.ScriptInitializationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-08 at 23
 */
//OPT we can generate setters and make method handles
@Service
public class OwnerPropertyInitializer implements ScriptInitializerGenerator{

    @Override
    public Consumer<? extends Script> getInitializer(Class<? extends Script> scriptClass) {
        List<OwnerPropertyDependency> ownerPropertyDependencies = loadDependencies(scriptClass);
        return s -> loadProperties(s, ownerPropertyDependencies);
    }

    private List<OwnerPropertyDependency> loadDependencies(Class<? extends Script> scriptClass) {
        List<OwnerPropertyDependency> dependencies = new ArrayList<>();
        Field[] declaredFields = scriptClass.getDeclaredFields();
        for (Field field : declaredFields) {
            OwnerProperty annotation = field.getAnnotation(OwnerProperty.class);
            if (annotation != null) {
                OwnerPropertyDependency dependency = new OwnerPropertyDependency(annotation, field);
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }


    private void loadProperties(Script script, List<OwnerPropertyDependency> dependencies) {
        for(OwnerPropertyDependency dependency : dependencies) {
            loadProperty(script, dependency);
        }
    }

    private void loadProperty(Script script, OwnerPropertyDependency dependency) {
        Component owner = script.getOwner();
        OwnerProperty ownerProperty = dependency.getOwnerProperty();
        if (!owner.hasProperty(ownerProperty.name()))
            throw new ScriptInitializationException(
                    script.getClass(),
                    new IllegalStateException("Component has no property named " + ownerProperty.name() + ".")
            );
        else {
            Property property = owner.getProperty(ownerProperty.name());
            setProperty(script, dependency.getPropertyField(), property);
        }
    }

    private void setProperty(Script script, Field propertyField, Property property) {
        try {
            propertyField.setAccessible(true);
            propertyField.set(script, property);
        } catch (Throwable throwable) {
            throw new ScriptInitializationException(
                    script.getClass(),
                    "Failed to set field to: " + property.getName(),
                    throwable
            );
        }
    }

    private static class OwnerPropertyDependency {
        private OwnerProperty ownerProperty;
        private Field propertyField;

        public OwnerPropertyDependency(OwnerProperty ownerProperty, Field propertyField) {
            this.ownerProperty = ownerProperty;
            this.propertyField = propertyField;
        }

        public OwnerProperty getOwnerProperty() {
            return ownerProperty;
        }

        public Field getPropertyField() {
            return propertyField;
        }
    }
}
