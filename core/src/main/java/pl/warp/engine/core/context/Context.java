package pl.warp.engine.core.context;

import pl.warp.engine.core.context.loader.ContextLoader;
import pl.warp.engine.core.context.loader.JavaContextHolder;

import java.util.Optional;

/**
 * @author Jaca777
 * Created 2017-08-29 at 22
 */
public class Context {
    private JavaContextHolder contextHolder;

    public Context(JavaContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }


    public static Context create() {
        ContextLoader loader = new ContextLoader();
        return new Context(loader.loadContext());
    }

    public <T> Optional<T> findOne(Class<T> type) {
        return contextHolder.findOne(type, Optional.empty());
    }

    public <T> Optional<T> findOne(Class<T> type, String qualifier) {
        return contextHolder.findOne(type, Optional.of(qualifier));
    }
}
