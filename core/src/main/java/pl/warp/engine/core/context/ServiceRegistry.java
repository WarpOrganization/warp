package pl.warp.engine.core.context;

/**
 * @author Jaca777
 * Created 2017-09-23 at 15
 */
public interface ServiceRegistry {
    void registerService(Object service);
    int order();
}
