package pl.warp.engine.core.context.config;

import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import pl.warp.engine.core.context.ServiceRegistry;
import pl.warp.engine.core.context.executor.ExecutorManager;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.execution.Executor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */

@Service
public class ConfigurationManager implements ServiceRegistry {

    private ExecutorManager executorManager;

    public ConfigurationManager(ExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    private Multimap<String, RegisteredObserver> registeredObservers = TreeMultimap.create(
            Ordering.natural(), Comparator.comparing(Object::hashCode)
    );

    public void updateValue(String name, Object value) {
        Collection<RegisteredObserver> observers = registeredObservers.get(name);
        observers.forEach(callHandle(value));
    }

    protected Consumer<RegisteredObserver> callHandle(Object value) {
        return observer -> {
            Executor executor = executorManager.getExecutor(observer.executor);
            if(executor == null)
                throw new ServiceConfigurationException("Executor " + observer.executor + " not found.");
            else executor.scheduleOnce(() -> {
                try {
                    observer.getHandle().invoke(value);
                } catch (Throwable throwable) {
                    throw new ServiceConfigurationException(throwable);
                }

            });
        };
    }

    @Override
    public void registerService(Object service) {
        if (hasConfigurationEnabled(service))
            processRegistration(service);
    }

    @Override
    public int order() {
        return 0;
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
            registeredObservers.put(configValue.value(), new RegisteredObserver(handle, configValue.dispatcher()));
        } catch (IllegalAccessException e) {
            throw new ServiceConfigurationException("Failed to load config value setter.", service);
        }
    }

    private static class RegisteredObserver {
        private MethodHandle handle;
        private String executor;

        public RegisteredObserver(MethodHandle handle, String executor) {
            this.handle = handle;
            this.executor = executor;
        }

        public MethodHandle getHandle() {
            return handle;
        }

        public String getExecutor() {
            return executor;
        }
    }
}
