package pl.warp.engine.core.scene.listenable;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-07-07 at 01
 */
public abstract class ListenableParent extends Component {
    public ListenableParent(Component parent) {
        super(parent);
    }

    public ListenableParent(EngineContext context) {
        super(context);
    }

    @Override
    protected void addChild(Component child) {
        super.addChild(child);
        System.out.println("atusiedzieje");
        triggerEvent(new ChildAddedEvent(child));
    }

    @Override
    protected void removeChild(Component child) {
        super.removeChild(child);
        triggerEvent(new ChildRemovedEvent(child));
    }
}
