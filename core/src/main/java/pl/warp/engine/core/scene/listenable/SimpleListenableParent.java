package pl.warp.engine.core.scene.listenable;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 01
 */
public class SimpleListenableParent extends ListenableParent{
    public SimpleListenableParent(Component parent) {
        super(parent);
    }

    public SimpleListenableParent(EngineContext context) {
        super(context);
    }
}
