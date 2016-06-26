package pl.warp.engine.core.scene;

import pl.warp.engine.core.EngineContext;
import pl.warp.engine.core.scene.Component;

/**
 * @author Jaca777
 *         Created 2016-06-26 at 15
 */
public abstract class Script<T extends Component> {
    private T component = null;
    private EngineContext context = null;

    public Script(T component) {
        this.component = component;
        this.context = component.getContext();
        component.addScript(this);
        onInit();
    }

    public T getComponent() {
        return component;
    }

    protected EngineContext getContext() {
        return context;
    }

    public abstract void onInit();

    public abstract void onUpdate(long delta);
}
