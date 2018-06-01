package net.warpgame.engine.core.script.initialization;

import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.ScriptInitializationException;
import net.warpgame.engine.core.script.annotation.ContextService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-17 at 01
 */

@Service
public class ServiceDependencyInitializer implements ScriptInitializerGenerator  {

    private Context context;

    public ServiceDependencyInitializer(Context context) {
        this.context = context;
    }

    @Override
    public Consumer<? extends Script> getInitializer(Class<? extends Script> scriptClass) {
        List<ServiceDependency> serviceDependencies = getDependencies(scriptClass);
        return e -> setDependencies(e, serviceDependencies);
    }

    private List<ServiceDependency> getDependencies(Class<? extends Script> scriptClass) {
        List<ServiceDependency> dependencies = new ArrayList<>();
        Field[] declaredFields = scriptClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ContextService annotation = field.getAnnotation(ContextService.class);
            if (annotation != null) {
                Object serviceInstance = getInstance(scriptClass, field.getType());
                ServiceDependency dependency = new ServiceDependency(serviceInstance, field);
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }

    private Object getInstance(Class<? extends Script> scriptClass, Class<?> serviceType) {
        return context.findOne(serviceType)
                .orElseThrow(() -> new ScriptInitializationException(
                        scriptClass,
                        "Failed to find service of type" + serviceType.getName())
                );
    }

    private void setDependencies(Script s, List<ServiceDependency> serviceDependencies) {
        for(ServiceDependency dependency : serviceDependencies)
            setServiceField(s, dependency.getServiceField(), dependency.getServiceInstance());
    }

    private void setServiceField(Script script, Field propertyField, Object serviceInstance) {
        try {
            propertyField.setAccessible(true);
            propertyField.set(script, serviceInstance);
        } catch (Throwable throwable) {
            throw new ScriptInitializationException(
                    script.getClass(),
                    "Failed to set service field " + propertyField.getName(),
                    throwable
            );
        }
    }


    private static class ServiceDependency {
        private Object serviceInstance;
        private Field serviceField;

        public ServiceDependency(Object serviceInstance, Field serviceField) {
            this.serviceInstance = serviceInstance;
            this.serviceField = serviceField;
        }

        public Object getServiceInstance() {
            return serviceInstance;
        }

        public Field getServiceField() {
            return serviceField;
        }
    }
}
