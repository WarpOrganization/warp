package pl.warp.engine.core.component;

import pl.warp.engine.core.EngineContext;

/**
 * @author Jaca777
 *         Created 2016-07-01 at 23
 */
public class SimpleComponent extends SceneComponent {

    public SimpleComponent(EngineContext context) {
        super(context);
    }

    public SimpleComponent(Component parent) {
        super(parent);
    }

}
