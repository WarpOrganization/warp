package pl.warp.engine.core.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2017-08-26 at 21
 */
public class ServiceRegistry {

    private Map<String, Collection<Object>> registeredServices;

    public ServiceRegistry() {
        this.registeredServices = new HashMap<>();
    }

    


}
