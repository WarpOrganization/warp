package pl.warp.engine.core.context.config;

import pl.warp.engine.core.context.ServiceRegistry;
import pl.warp.engine.core.context.executor.ExecutorManager;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.Executor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

/**
 * @author Jaca777
 * Created 2017-09-23 at 17
 */
@Service
public class ConfigurationServiceRegistry  implements ServiceRegistry {

    private ExecutorManager executorManager;
    private Config config;
    private ConfigurationManager configurationManager;

    public ConfigurationServiceRegistry(ExecutorManager executorManager, Config config, ConfigurationManager configurationManager) {
        this.executorManager = executorManager;
        this.config = config;
        this.configurationManager = configurationManager;
    }

    @Override
    public void registerService(Object service) {
        if (hasConfigurationEnabled(service))
            processRegistration(service);
    }

    private boolean hasConfigurationEnabled(Object service) {
        return service.getClass().getAnnotation(EnableConfig.class) != null;
    }

    private void processRegistration(Object service) {
        Method[] methods = service.getClass().getMethods();
        for (Method method : methods) {
            ConfigValue configValue = method.getAnnotation(ConfigValue.class);
            if (configValue != null) {
                registerValueObserver(service, method, configValue);
            }
        }
    }

    private void registerValueObserver(Object service, Method method, ConfigValue configValue) {
        if (method.getParameterCount() != 1)
            throw new ServiceConfigurationException("Config value setter needs to have exactly 1 param.", service);
        try {
            MethodHandle handle = MethodHandles.lookup()
                    .unreflect(method)
                    .bindTo(service);
            Executor executor = executorManager.getExecutor(configValue.dispatcher());
            RegisteredObserver observer = new RegisteredObserver(handle, executor);
            configurationManager.addObserver(configValue.value(), observer);
            setValue(configValue.value(), observer);
        } catch (IllegalAccessException e) {
            throw new ServiceConfigurationException("Failed to load config value setter.", service);
        }
    }

    private void setValue(String name, RegisteredObserver observer) {
        Object value = config.getValue(name);
        if(value != null) {
            observer.getExecutor().scheduleOnce(() -> {
                try {
                    observer.getHandle().invoke(value);
                } catch (Throwable throwable) {
                    throw new ServiceConfigurationException("Failed to set config value to: " + name);
                }
            });
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
