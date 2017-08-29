package pl.warp.engine.core.context;

import pl.warp.engine.core.context.loader.ContextLoader;

/**
 * @author Jaca777
 * Created 2017-08-29 at 22
 */
public class Context {
    public static void create() {
        ContextLoader contextLoader = new ContextLoader();
        contextLoader.loadContext();
    }
}
