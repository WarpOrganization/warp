package pl.warp.engine.core.component.listenable;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.SceneComponent;

/**
 * @author Jaca777
 *         Created 2016-07-07 at 01
 */
public abstract class ListenableParent extends SceneComponent {
    public ListenableParent(EngineContext context) {
        super(context);
    }

    public ListenableParent(Component parent) {
        super(parent);
    }

    @Override
    public void addChild(Component child) {
        super.addChild(child);
        triggerEvent(new ChildAddedEvent(child));
    }

    @Override
    public void removeChild(Component child) {
        super.removeChild(child);
        triggerEvent(new ChildRemovedEvent(child));
    }
}
