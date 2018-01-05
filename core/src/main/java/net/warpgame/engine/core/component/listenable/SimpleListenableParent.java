package net.warpgame.engine.core.component.listenable;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 01
 */
public class SimpleListenableParent extends ListenableParent{
    public SimpleListenableParent(EngineContext context) {
        super(context);
    }

    public SimpleListenableParent(Component parent) {
        super(parent);
    }
}
