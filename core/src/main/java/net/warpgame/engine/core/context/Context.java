package net.warpgame.engine.core.context;

import com.google.common.collect.ImmutableSet;
import net.warpgame.engine.core.context.loader.ContextLoader;
import net.warpgame.engine.core.context.loader.JavaContextHolder;
import net.warpgame.engine.core.context.loader.service.ServiceInfo;
import scala.Option;

import java.util.*;

/**
 * @author Jaca777
 * Created 2017-08-29 at 22
 */
public class Context {

    private Set<String> profiles;
    private JavaContextHolder contextHolder;

    public static Context create(String... profiles) {
        ContextLoader loader = new ContextLoader();
        Context context = new Context();
        JavaContextHolder contextHolder = loader.loadContext(context, profiles);
        context.setContextHolder(contextHolder);
        context.setProfiles(profiles);
        context.processRegistration();
        return context;
    }

    private void setProfiles(String[] profiles) {
        this.profiles = ImmutableSet.copyOf(profiles);
    }

    public Set<String> getProfiles() {
        return profiles;
    }

    public boolean isProfileEnabled(String profile) {
        return this.profiles.contains(profile);
    }

    private void processRegistration() {
        List<ServiceRegistry> registries = new ArrayList<>(findAll(ServiceRegistry.class));
        registries.sort(Comparator.comparingInt(ServiceRegistry::getPriority));
        for (ServiceRegistry registry : registries) {
            for (Object service : contextHolder.getAllServices()) {
                registry.registerService(service);
            }
            registry.finalizeRegistration();
        }
    }

    public <T> Optional<T> findOne(Class<T> type) {
        return contextHolder.findOne(type, Optional.empty());
    }

    public <T> Optional<T> findOne(Class<T> type, String qualifier) {
        return contextHolder.findOne(type, Optional.of(qualifier));
    }

    public <T> List<T> findAll(Class<T> type) {
        return contextHolder.findAll(type, Optional.empty());
    }

    public <T> List<T> findAll(Class<T> type, String qualifier) {
        return contextHolder.findAll(type, Optional.of(qualifier));
    }

    public void addService(Object service) {
        ServiceInfo serviceInfo = new ServiceInfo(service.getClass(), Option.empty(), null, null);
        contextHolder.add(serviceInfo, service);
    }

    private void setContextHolder(JavaContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }
}
